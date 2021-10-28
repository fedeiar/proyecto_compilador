package tablaDeSimbolos;

import tablaDeSimbolos.nodosAST.*;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoBloque;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class Unidad {
    
    protected Map<String, ParametroFormal> parametros;
    protected List<ParametroFormal> lista_parametros; //la lista es porque necesitamos el orden de los parametros

    protected boolean esDinamico; //TODO: esta bien?
    protected NodoBloque bloque;


    public Unidad(){
        parametros = new HashMap<>();
        lista_parametros = new ArrayList<>();
    }

    public List<ParametroFormal> getListaParametros(){
        return lista_parametros;
    }

    public void insertarParametro(ParametroFormal parametro) throws ExcepcionSemantica{ //TODO: cambiar por un toString y eliminar el parametro nombreParametro
        if(parametros.get(parametro.toString()) == null){
            parametros.put(parametro.toString(), parametro);
            lista_parametros.add(parametro);
        } else{
            throw new ExcepcionSemantica(parametro.getTokenIdVar(), "ya existe otro parametro con el mismo nombre en la unidad");
        }
    }

    public ParametroFormal getParametroFormal(String nombreParametroFormal){
        return parametros.get(nombreParametroFormal);
    }

    public boolean mismosParametros(Unidad unidad2){
        List<ParametroFormal> lista_parametros_unidad2 = unidad2.getListaParametros();
        int i = 0;
        boolean mismosParametros = true;
        if(lista_parametros.size() == lista_parametros_unidad2.size()){
            for(ParametroFormal p1 : lista_parametros){
                ParametroFormal p2 = lista_parametros_unidad2.get(i);
                i++;
                if(!p1.mismoTipo(p2)){
                    mismosParametros = false;
                    break;
                }
            }
        } else{
            mismosParametros = false;
        }
        return mismosParametros;
    }

    public boolean esDinamico(){
        return esDinamico;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica{
        for(ParametroFormal p : parametros.values()){
            p.estaBienDeclarado();
        }
    }

    public void insertarBloque(NodoBloque bloque){ 
        this.bloque = bloque;
    }

    public void chequearSentencias() throws ExcepcionSemantica{
        TablaSimbolos.unidadActual = this;
        bloque.chequear();
    }
    
}
