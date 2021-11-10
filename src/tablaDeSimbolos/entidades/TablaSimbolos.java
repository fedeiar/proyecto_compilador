package tablaDeSimbolos.entidades;

import tablaDeSimbolos.metodosPredefinidos.MetodoDebugPrint;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoBloque;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoVarLocal;
import tablaDeSimbolos.tipos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class TablaSimbolos {
    

    private static TablaSimbolos instance;

    private static Map<String, Clase> clases;

    public static Clase claseActual;
    public static Unidad unidadActual;
    public static List<NodoBloque> stackBloqueActual;

    public static List<String> instruccionesMaquina;

    private static Metodo metodoMainMiniJava;

    private TablaSimbolos(){
        clases = new HashMap<String, Clase>();
        stackBloqueActual = new ArrayList<NodoBloque>();
        instruccionesMaquina = new ArrayList<>();

        try{
            Metodo metodo; 

            //Creando Object
            Token tokenObject = new Token(TipoDeToken.id_clase, "Object", 0);
            Clase claseObject = new Clase(tokenObject); 
            claseObject.set_idClaseAncestro(null); // Object va a ser la unica clase que tenga ancestro null.

            metodo = new MetodoDebugPrint(new Token(TipoDeToken.id_metVar, "debugPrint", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseObject.insertarMetodo(metodo); 

            //TODO: hacer las clases particulares para cada Metodo de System que redefina generarCodigo()

            //Creando System
            Token tokenSystem = new Token(TipoDeToken.id_clase, "System", 0);
            Clase claseSystem = new Clase(tokenSystem);
            claseSystem.set_idClaseAncestro(tokenObject);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "read", 0), false, new TipoInt(), tokenSystem);
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printB", 0), false, new TipoVoid(), tokenSystem);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printC", 0), false, new TipoVoid(), tokenSystem);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "c", 0), new TipoChar()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printI", 0), false, new TipoVoid(), tokenSystem);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printS", 0), false, new TipoVoid(), tokenSystem);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "s", 0), new TipoString()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "println", 0), false, new TipoVoid(), tokenSystem);
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printBln", 0), false, new TipoVoid(), tokenSystem);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printCln", 0), false, new TipoVoid(), tokenSystem); 
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "c", 0), new TipoChar()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printIln", 0), false, new TipoVoid(), tokenSystem);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printSln", 0), false, new TipoVoid(), tokenSystem);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "s", 0), new TipoString()));
            claseSystem.insertarMetodo(metodo);

            insertarClase("Object", claseObject);
            insertarClase("System", claseSystem);
        } catch(ExcepcionSemantica e){
            // NUNCA DEBERIA SUCEDER ESTE ERROR.
        }
    }

    public static TablaSimbolos getInstance(){
        if(instance == null){
            instance = new TablaSimbolos();
        }
        return instance;
    }

    public static void reiniciar(){
        instance = null;
        clases = null;
        claseActual = null;
        unidadActual = null;
        instruccionesMaquina = null;
    }


    public void insertarClase(String nombreClase, Clase clase) throws ExcepcionSemantica{
        if(clases.get(nombreClase) == null){
            clases.put(nombreClase, clase);
        } else{
            throw new ExcepcionSemantica(clase.getTokenIdClase(), "la clase "+clase.getTokenIdClase().getLexema()+" ya esta declarada"); 
        }
    }

    public static Clase getClase(String nombreClase){
        return clases.get(nombreClase);
    }

    public boolean existeClase(String nombreClase){
        Clase clase = clases.get(nombreClase);
        if(clase != null){
            return true;
        } else{
            return false;
        }
    }

    
    // Chequeo de declaraciones

    public void estaBienDeclarado() throws ExcepcionSemantica{
        for(Clase clase : clases.values()){
            clase.estaBienDeclarado();
        }

        existeMain();
    }

    private void existeMain() throws ExcepcionSemantica{ 
        Metodo metodoMain = new Metodo(new Token(TipoDeToken.id_metVar, "main", 0), false, new TipoVoid(), null);
        boolean estaMain = false;
        Metodo metodoMainOriginal;
        for(Clase clase : clases.values()){
            metodoMainOriginal = clase.getMetodoMismaSignatura(metodoMain);
            if(estaMain && metodoMainOriginal != null){
                throw new ExcepcionSemantica(metodoMainOriginal.getTokenIdMet(), "se encontro mas de una declaracion del metodo main");
            }
            if(metodoMainOriginal != null){
                estaMain = true;
                metodoMainMiniJava = metodoMainOriginal;
            }
        }

        if(!estaMain){
            throw new ExcepcionSemantica(metodoMain.getTokenIdMet(), "debe declararse el metodo main() en alguna clase");
        }
    }

    public void consolidar() throws ExcepcionSemantica{
        for(Clase clase : clases.values()){
            System.out.println("\n----ESTOY EN: "+clase.getTokenIdClase().getLexema()+"----\n");
            clase.consolidar();
            
            for(Map.Entry<String,Atributo> atr : clase.getHashAtributos().entrySet()){
                System.out.println(atr.getKey()+" "+atr.getValue().getOffset());
            }
            
            /*
            for(Metodo metodo : clase.getMetodos()){
                System.out.println(metodo.toString()+" "+metodo.getOffset());
            }
            */
        }
    }

    // Chequeo de sentencias

    public void chequeoSentencias() throws ExcepcionSemantica{ 
        for(Clase clase : clases.values()){
            clase.chequearSentencias();
        }
    }

    // Operaciones sobre el bloque actual

    public static void apilarBloqueActual(NodoBloque bloque){ 
        stackBloqueActual.add(0, bloque);
    }

    public static void desapilarBloqueActual(){
        stackBloqueActual.remove(0);
    }

    public static NodoBloque getBloqueActual(){ 
        return stackBloqueActual.get(0);
    }

    public static boolean hayBloque(){
        return stackBloqueActual.size() > 0;
    }

    public static NodoVarLocal getVarLocalUnidadActual(String nombreVarLocal){
        for(NodoBloque bloque : stackBloqueActual){
            NodoVarLocal varLocal = bloque.getVarLocalBloque(nombreVarLocal);
            if(varLocal != null){
                return varLocal;
            }
        }
        return null;
    }

    // Generacion de codigo intermedio

    public static void generarCodigo(){
        System.out.println("\n-----------------GENERANDO CODIGO----------------\n");
        generarLlamadaMain();
        generarRutinasHeap();
        for(Clase clase : clases.values()){
            clase.generarCodigo(); // Generamos las VT de cada clase
        }
    }

    private static void generarLlamadaMain(){
        instruccionesMaquina.add(".CODE\n");
        instruccionesMaquina.add("PUSH simple_heap_init \n"+
        "CALL\n"+
        "PUSH "+ metodoMainMiniJava.toStringLabel() +"\n"+
        "CALL\n"+
        "HALT\n");
    }

    private static void generarRutinasHeap(){
        instruccionesMaquina.add("simple_heap_init: \n"+
        "RET 0 ; Retorna inmediatamente\n");
    
        instruccionesMaquina.add("simple_malloc:\n"+
        "LOADFP	; Inicialización unidad\n"+
        "LOADSP\n"+
        "STOREFP ; Finaliza inicialización del RA\n"+
        "LOADHL	; hl\n"+
        "DUP	; hl\n"+
        "PUSH 1	; 1\n"+
        "ADD	; hl+1\n"+
        "STORE 4 ; Guarda el resultado (un puntero a la primer celda de la región de memoria)\n"+
        "LOAD 3	; Carga la cantidad de celdas a alojar (parámetro que debe ser positivo)\n"+
        "ADD\n"+
        "STOREHL ; Mueve el heap limit (hl). Expande el heap\n"+
        "STOREFP\n"+
        "RET 1	; Retorna eliminando el parámetro\n\n\n"
        );
    }
}
