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
