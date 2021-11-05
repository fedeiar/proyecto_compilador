package tablaDeSimbolos.entidades;

import tablaDeSimbolos.nodosAST.nodosSentencia.NodoBloque;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoVarLocal;
import tablaDeSimbolos.tipos.*;

import java.security.KeyStore.Entry;
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

    private TablaSimbolos(){
        clases = new HashMap<String, Clase>();
        stackBloqueActual = new ArrayList<NodoBloque>();

        try{
            Metodo metodo;

            //Creando Object
            Token tokenObject = new Token(TipoDeToken.id_clase, "Object", 0);
            Clase claseObject = new Clase(tokenObject); 
            claseObject.set_idClaseAncestro(null); // Object va a ser la unica clase que tenga ancestro null.

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "debugPrint", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseObject.insertarMetodo(metodo); 

            //Creando System
            Clase claseSystem = new Clase(new Token(TipoDeToken.id_clase, "System", 0));
            claseSystem.set_idClaseAncestro(tokenObject);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "read", 0), false, new TipoInt(), tokenObject);
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printB", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printC", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "c", 0), new TipoChar()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printI", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printS", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "s", 0), new TipoString()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "println", 0), false, new TipoVoid(), tokenObject);
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printBln", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printCln", 0), false, new TipoVoid(), tokenObject); 
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "c", 0), new TipoChar()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printIln", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseSystem.insertarMetodo(metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printSln", 0), false, new TipoVoid(), tokenObject);
            metodo.insertarParametro(new ParametroFormal(new Token(TipoDeToken.id_metVar, "s", 0), new TipoString()));
            claseSystem.insertarMetodo(metodo);

            insertarClase("Object", claseObject);
            insertarClase("System", claseSystem);
        } catch(ExcepcionSemantica e){
            //NUNCA DEBERIA SUCEDER ESTE ERROR.
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
            }
        }

        if(!estaMain){
            throw new ExcepcionSemantica(metodoMain.getTokenIdMet(), "debe declararse el metodo main() en alguna clase");
        }
    }

    public void consolidar() throws ExcepcionSemantica{
        for(Clase clase : clases.values()){
            System.out.println("estoy en "+clase.getTokenIdClase().getLexema());
            clase.consolidar();

            for(String a : clase.getHashAtributos().keySet()){
                System.out.println(a);
            }
            
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

    public static NodoVarLocal getVarLocalUnidadActual(String nombreVarLocal){ //TODO: aca esta bien?
        for(NodoBloque bloque : stackBloqueActual){
            NodoVarLocal varLocal = bloque.getVarLocalBloque(nombreVarLocal);
            if(varLocal != null){
                return varLocal;
            }
        }
        return null;
    }
}
