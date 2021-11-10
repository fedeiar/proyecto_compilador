package tablaDeSimbolos.nodosAST.nodosSentencia;

import java.util.List;
import java.util.Map;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;

import java.util.ArrayList;
import java.util.HashMap;

public class NodoBloque extends NodoSentencia{
    
    private List<NodoSentencia> listaSentencias;
    private Map<String, NodoVarLocal> varLocales;

    public NodoBloque(){
        listaSentencias = new ArrayList<>();
        varLocales = new HashMap<>();
    }

    public void insertarSentencia(NodoSentencia sentencia){
        listaSentencias.add(sentencia);
    }

    public NodoVarLocal getVarLocalBloque(String nombreVarLocal){
        return varLocales.get(nombreVarLocal);
    }

    public void insertarVarLocal(NodoVarLocal varLocal) throws ExcepcionSemantica{
        if(TablaSimbolos.getVarLocalUnidadActual(varLocal.toString()) != null){
            throw new ExcepcionSemantica(varLocal.getToken(), "la variable "+varLocal.toString()+" esta duplicada en este bloque o un bloque contenedor");
        }
        
        if(TablaSimbolos.unidadActual.getParametroFormal(varLocal.toString()) != null){
            throw new ExcepcionSemantica(varLocal.getToken(), "la variable "+varLocal.toString()+" esta duplicada ya que existe un parametro con el mismo nombre");
        }
        
        TablaSimbolos.unidadActual.agregarOffsetVarLocal(varLocal);
        varLocales.put(varLocal.toString(), varLocal);
    }


    public void chequear() throws ExcepcionSemantica{
        TablaSimbolos.apilarBloqueActual(this); 
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.chequear();
        }
        TablaSimbolos.desapilarBloqueActual();
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.generarCodigo();
        }
    }

    
}
