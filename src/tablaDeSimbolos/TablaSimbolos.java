package tablaDeSimbolos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class TablaSimbolos {
    

    private static TablaSimbolos instance;

    private Map<String, Clase> clases;

    public Clase claseActual;
    public Unidad unidadActual;
    //TODO: no nos interesan mas entidades actuales que estas no?

    private TablaSimbolos() throws ExcepcionSemantica{
        clases = new HashMap<String, Clase>();
        
        Metodo metodo;
        Token tokenObject = new Token(TipoDeToken.id_clase, "Object", 0);
        Clase claseObject = new Clase(tokenObject); 
        claseObject.set_idClaseAncestro(null);//Object va a ser la unica clase que tenga ancestro null.
        metodo = new Metodo(new Token(TipoDeToken.id_metVar, "debugPrint", 0), TipoDeToken.pr_static, new TipoVoid());
        metodo.insertarParametro("i", new ParametroFormal(new Token(TipoDeToken.id_metVar, "i", 0), new TipoInt()));
        claseObject.insertarMetodo("debugPrint", metodo); 

        Clase claseSystem = new Clase(new Token(TipoDeToken.id_clase, "System", 0));
        claseSystem.set_idClaseAncestro(tokenObject);
        metodo = new Metodo(new Token(TipoDeToken.id_metVar, "read", 0), TipoDeToken.pr_static, new TipoInt());
        claseSystem.insertarMetodo("read", metodo);
        metodo = new Metodo(new Token(TipoDeToken.id_metVar, "read", 0), TipoDeToken.pr_static, new TipoInt());
        metodo.insertarParametro("b", new ParametroFormal(new Token(TipoDeToken.id_metVar, "b", 0), new TipoBoolean()));
        claseSystem.insertarMetodo("read", metodo);
        //TODO: terminar de insertar los metodos en System.

        insertarClase("Object", claseObject);
        insertarClase("System", claseSystem);
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


    public boolean existeClase(Token tokenIdClase){
        Clase clase = clases.get(tokenIdClase.getLexema());
        if(clase != null){
            return true;
        } else{
            return false;
        }
    }

    
    public void estaBienDeclarado() throws ExcepcionSemantica{
        //TODO
        for(Clase clase : clases.values()){
            clase.estaBienDeclarado();
        }
    }

    public void consolidar() throws ExcepcionSemantica{
        //TODO
    }
}
