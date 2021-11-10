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

    private int offsetDisponibleRA;

    public NodoBloque(){
        listaSentencias = new ArrayList<>();
        varLocales = new HashMap<>();

        this.offsetDisponibleRA = -1; // Inicialmente no se sabe el offset disponible, le deberá preguntar a la unidad o bloque que lo contenga.
    }

    public int getOffsetDisponibleEnRA(){
        return offsetDisponibleRA;
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
        
        agregarOffsetVarLocalRA(varLocal); // TODO: esta bien?
        varLocales.put(varLocal.toString(), varLocal);
    }

    private void agregarOffsetVarLocalRA(NodoVarLocal nodoVarLocal){
        nodoVarLocal.setOffset(offsetDisponibleRA);
        offsetDisponibleRA++;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien el tema de los offset?
        if(TablaSimbolos.hayBloque()){
            this.offsetDisponibleRA = TablaSimbolos.getBloqueActual().getOffsetDisponibleEnRA();
        } else{
            this.offsetDisponibleRA = TablaSimbolos.unidadActual.getOffsetDisponibleEnRA();
        }

        TablaSimbolos.apilarBloqueActual(this); 
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.chequear();
        }
        TablaSimbolos.desapilarBloqueActual();
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ // TODO: esta bien?
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.generarCodigo();
        }
    }

    
}
