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

    protected int offsetDisponibleVariableLocal;

    public NodoBloque(){
        listaSentencias = new ArrayList<>();
        varLocales = new HashMap<>();
    }

    public int getOffsetDisponibleVariableLocal(){
        return offsetDisponibleVariableLocal;
    }

    public int getCantVarLocalesEnUnidad(){
        return offsetDisponibleVariableLocal * -1;
    }

    public void insertarSentencia(NodoSentencia sentencia){
        listaSentencias.add(sentencia);
    }

    public NodoVarLocal getVarLocalBloque(String nombreVarLocal){
        return varLocales.get(nombreVarLocal);
    }

    public void insertarVarLocal(NodoVarLocal nodoVarLocal) throws ExcepcionSemantica{
        if(TablaSimbolos.getVarLocalUnidadActual(nodoVarLocal.toString()) != null){
            throw new ExcepcionSemantica(nodoVarLocal.getToken(), "la variable "+nodoVarLocal.toString()+" esta duplicada en este bloque o un bloque contenedor");
        }
        
        if(TablaSimbolos.unidadActual.getParametroFormal(nodoVarLocal.toString()) != null){
            throw new ExcepcionSemantica(nodoVarLocal.getToken(), "la variable "+nodoVarLocal.toString()+" esta duplicada ya que existe un parametro con el mismo nombre");
        }
        

        agregarOffsetVarLocal(nodoVarLocal);
        varLocales.put(nodoVarLocal.toString(), nodoVarLocal);
    }

    private void agregarOffsetVarLocal(NodoVarLocal nodoVarLocal){
        nodoVarLocal.setOffset(offsetDisponibleVariableLocal);
        offsetDisponibleVariableLocal--;
    }


    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien como se asignan los offsets?
        if(TablaSimbolos.hayBloque()){
            offsetDisponibleVariableLocal = TablaSimbolos.getBloqueActual().getOffsetDisponibleVariableLocal(); // los offset de sus varLocales comienzan desde el offset disponible de su bloque contenedor.
        } else{
            offsetDisponibleVariableLocal = 0;
        }

        TablaSimbolos.apilarBloqueActual(this); 
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.chequear();
        }
        TablaSimbolos.desapilarBloqueActual();
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ // TODO: esta bien como se liberan las varLocales?
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.generarCodigo();
        }
        
        TablaSimbolos.instruccionesMaquina.add("FMEM "+this.varLocales.size()+" ; Liberamos las variables locales utilizadas en el bloque actual");
    }

    
}
