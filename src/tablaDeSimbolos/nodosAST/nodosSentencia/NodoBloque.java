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

    public int getOffsetDisponibleVarLocal(){
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

    // Chequeo de sentencias

    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien como se asignan los offsets?
        this.establecerOffsetDisponibleVarLocal();

        TablaSimbolos.apilarBloqueActual(this); 
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.chequear();
        }
        TablaSimbolos.desapilarBloqueActual();
    }

    public void establecerOffsetDisponibleVarLocal(){
        if(TablaSimbolos.hayBloque()){
            this.offsetDisponibleVariableLocal = TablaSimbolos.getBloqueActual().getOffsetDisponibleVarLocal(); // Los offset de las varLocales del bloque a agregar comienzan desde el offset disponible de su bloque contenedor.
        } else{
            this.offsetDisponibleVariableLocal = 0;
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ // TODO: esta bien como se liberan las varLocales?
        TablaSimbolos.apilarBloqueActual(this);
        for(NodoSentencia sentencia : listaSentencias){
            sentencia.generarCodigo();
        }
        TablaSimbolos.desapilarBloqueActual();
        
        TablaSimbolos.instruccionesMaquina.add("FMEM "+this.varLocales.size()+" ; Liberamos las variables locales utilizadas en el bloque actual");
    }

    
}
