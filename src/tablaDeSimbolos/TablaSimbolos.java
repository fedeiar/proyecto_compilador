package tablaDeSimbolos;


import java.util.HashMap;
import java.util.Map;


import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class TablaSimbolos {
    

    private static TablaSimbolos instance;

    private static Map<String, Clase> clases;

    public static Clase claseActual;
    public static Unidad unidadActual;
    //TODO: no nos interesan mas entidades actuales que estas no?

    private TablaSimbolos(){
        clases = new HashMap<String, Clase>();
        
        try{
            Metodo metodo;

            //Creando Object
            Token tokenObject = new Token(TipoDeToken.id_clase, "Object", 0);
            Clase claseObject = new Clase(tokenObject); 
            claseObject.set_idClaseAncestro(null);//Object va a ser la unica clase que tenga ancestro null.

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "debugPrint", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("i", new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseObject.insertarMetodo("debugPrint", metodo); 

            //Creando System
            Clase claseSystem = new Clase(new Token(TipoDeToken.id_clase, "System", 0));
            claseSystem.set_idClaseAncestro(tokenObject);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "read", 0), TipoDeToken.pr_static, new TipoInt());
            claseSystem.insertarMetodo("read", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printB", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("b", new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
            claseSystem.insertarMetodo("printB", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printC", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("c", new ParametroFormal(new Token(TipoDeToken.id_metVar, "c", 0), new TipoChar()));
            claseSystem.insertarMetodo("printC", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printI", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("i", new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseSystem.insertarMetodo("printI", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printS", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("s", new ParametroFormal(new Token(TipoDeToken.id_metVar, "s", 0), new TipoString()));
            claseSystem.insertarMetodo("printS", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "println", 0), TipoDeToken.pr_static, new TipoVoid());
            claseSystem.insertarMetodo("println", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printBln", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("b", new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
            claseSystem.insertarMetodo("printBln", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printCln", 0), TipoDeToken.pr_static, new TipoVoid()); 
            metodo.insertarParametro("c", new ParametroFormal(new Token(TipoDeToken.id_metVar, "c", 0), new TipoChar()));
            claseSystem.insertarMetodo("printCln", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printIln", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("i", new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
            claseSystem.insertarMetodo("printIln", metodo);

            metodo = new Metodo(new Token(TipoDeToken.id_metVar, "printSln", 0), TipoDeToken.pr_static, new TipoVoid());
            metodo.insertarParametro("s", new ParametroFormal(new Token(TipoDeToken.id_metVar, "s", 0), new TipoString()));
            claseSystem.insertarMetodo("printSln", metodo);

            insertarClase("Object", claseObject);
            insertarClase("System", claseSystem);
        } catch(ExcepcionSemantica e){
            //NUNCA DEBERÍA SUCEDER ESTE ERROR.
        }
    }

    public static TablaSimbolos getInstance(){
        if(instance == null){
            instance = new TablaSimbolos();
        }
        return instance;
    }

    public static void reiniciar(){ //TODO: esta bien?
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

    
    //chequeo de declaraciones

    public void estaBienDeclarado() throws ExcepcionSemantica{
        for(Clase clase : clases.values()){
            clase.estaBienDeclarado();
        }

        existeMain();
    }

    private void existeMain() throws ExcepcionSemantica{ 
        Metodo metodoMain = new Metodo(new Token(TipoDeToken.id_metVar, "main", 0), TipoDeToken.pr_static, new TipoVoid());
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
            throw new ExcepcionSemantica(metodoMain.getTokenIdMet(), "debe declararse el metodo main() en alguna clase"); //TODO: aclarar en el informe que se señala el error en la linea 0
        }
    }

    public void consolidar() throws ExcepcionSemantica{
        for(Clase clase : clases.values()){
            clase.consolidar();
        }

    }
}
