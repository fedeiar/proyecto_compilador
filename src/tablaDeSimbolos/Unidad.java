package tablaDeSimbolos;

import tablaDeSimbolos.nodosAST.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class Unidad {
    
    protected Map<String, ParametroFormal> parametros;
    protected List<ParametroFormal> lista_parametros; //la lista es porque necesitamos el orden de los parametros

    protected NodoBloque bloque;


    public Unidad(){
        parametros = new HashMap<>();
        lista_parametros = new ArrayList<>();
    }


    public void insertarParametro(String nombreParametro, ParametroFormal parametro) throws ExcepcionSemantica{
        if(parametros.get(nombreParametro) == null){
            parametros.put(nombreParametro, parametro);
            lista_parametros.add(parametro);
        } else{
            throw new ExcepcionSemantica(parametro.getTokenIdVar(), "ya existe otro parametro con el mismo nombre en la unidad");
        }
    }

    public List<ParametroFormal> getListaParametros(){
        return lista_parametros;
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

    public void estaBienDeclarado() throws ExcepcionSemantica{
        for(ParametroFormal p : parametros.values()){
            p.estaBienDeclarado();
        }
    }

    public void insertarBloque(NodoBloque bloque){ 
        this.bloque = bloque;
    }

    public void chequearSentencias() throws ExcepcionSemantica{ //TODO: recordar que para los metodos deben chequearse solamente una vez en el lugar donde estan declarados y en ningun otro lugar. Hacerlo
        bloque.chequear();
    }
    
}
