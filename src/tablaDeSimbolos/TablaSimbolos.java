package tablaDeSimbolos;

import java.util.HashMap;
import java.util.Map;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class TablaSimbolos {
    

    private static TablaSimbolos instance;

    private Map<String, Clase> clases;

    public Clase claseActual;
    public Metodo metodoActual;
    public Constructor constructorActual; //TODO: esto esta bien?

    private TablaSimbolos() throws ExcepcionSemantica{
        clases = new HashMap<String, Clase>();
        //TODO: esta bien lo que se esta haciendo con object y system?
        Metodo metodo;
        Clase claseObject = new Clase(new Token(TipoDeToken.id_clase, "Object", 0)); 
        metodo = new Metodo(new Token(TipoDeToken.id_metVar, "debugPrint", 0), "static", new TipoVoid());
        metodo.insertarParametro("i", new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
        claseObject.insertarMetodo("debugPrint", metodo); 

        Clase claseSystem = new Clase(new Token(TipoDeToken.id_clase, "System", 0));
        metodo = new Metodo(new Token(TipoDeToken.id_metVar, "read", 0), "static", new TipoInt());
        claseSystem.insertarMetodo("read", metodo);
        metodo = new Metodo(new Token(TipoDeToken.id_metVar, "read", 0), "static", new TipoInt());
        metodo.insertarParametro("b", new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
        claseSystem.insertarMetodo("read", metodo);
        //TODO: si esta bien la idea terminar

        insertarClase("Object", claseObject);
        insertarClase("System.", claseSystem);
    }

    public static TablaSimbolos getInstance() throws ExcepcionSemantica{
        if(instance == null){
            instance = new TablaSimbolos();
        }
        return instance;
    }


    public void insertarClase(String nombreClase, Clase clase) throws ExcepcionSemantica{
        if(clases.get(nombreClase) == null){
            clases.put(nombreClase, clase);
        } else{
            throw new ExcepcionSemantica(clase.getTokenIdClase()); //el token actual lo saco de la variable clase
        }
    }


    public Clase existeClase(Token tokenIdClase) throws ExcepcionSemantica{
        Clase clase = clases.get(tokenIdClase.getLexema());
        if(clase != null){
            return clase;
        } else{
            throw new ExcepcionSemantica(tokenIdClase);
        }
    }

    
    public void estaBienDeclarado() throws ExcepcionSemantica{
        //TODO
    }

    public void consolidar() throws ExcepcionSemantica{
        //TODO
    }
}
