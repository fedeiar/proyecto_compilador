package tablaDeSimbolos.nodosAST;

import java.util.List;
import java.util.Map;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;

import java.util.ArrayList;
import java.util.HashMap;

public class NodoBloque extends NodoSentencia{
    
    private List<NodoSentencia> sentencias;
    private Map<String, NodoVarLocal> varLocales; //TODO: esta bien esto? luego insertamos las var locales al crearse?

    public NodoBloque(){
        sentencias = new ArrayList<>();
        varLocales = new HashMap<>();
    }

    public void insertarSentencia(NodoSentencia sentencia){
        if(sentencia != null){
            sentencias.add(sentencia);
        }
    }

    public NodoVarLocal getVarLocalBloque(String nombreVarLocal){ //TODO: esta bien?
        return varLocales.get(nombreVarLocal);
    }

    public NodoVarLocal getVarLocalMetodo(String nombreVarLocal){ //TODO: tal vez moverlo a TablaSimbolos.
        for(NodoBloque bloque : TablaSimbolos.stackBloqueActual){
            NodoVarLocal varLocal = bloque.getVarLocalBloque(nombreVarLocal);
            if(varLocal != null){
                return varLocal;
            }
        }
        return null;
    }

    public boolean existeVarLocalEnMetodo(String nombreVarLocal){ //TODO: tal vez moverlo a Metodo.
        return this.getVarLocalMetodo(nombreVarLocal) != null;
    }

    public void insertarVarLocal(NodoVarLocal varLocal) throws ExcepcionSemantica{ //TODO: esta bien este metodo insertarVarLocales? 
        
        if(this.existeVarLocalEnMetodo(varLocal.toString())){
            throw new ExcepcionSemantica(varLocal.getToken(), "la variable "+varLocal.toString()+" esta duplicada en este bloque o un bloque contenedor");
        }
        
        if(TablaSimbolos.unidadActual.getParametroFormal(varLocal.toString()) != null){
            throw new ExcepcionSemantica(varLocal.getToken(), "la variable "+varLocal.toString()+" esta duplicada ya que existe un parametro con el mismo nombre");
        }
        
        varLocales.put(varLocal.toString(), varLocal);
    }

    public void chequear() throws ExcepcionSemantica{
        TablaSimbolos.apilarBloqueActual(this); //TODO esta bien? entonces de esta manera tenemos siempre al tope el bloque más anidado.
        for(NodoSentencia sentencia : sentencias){
            sentencia.chequear();
        }

        TablaSimbolos.desapilarBloqueActual(); //TODO: esta bien desapilarlo aca despues de chequearse?
        //TODO
    }

    
}
