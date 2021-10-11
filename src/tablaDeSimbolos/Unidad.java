package tablaDeSimbolos;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class Unidad {
    
    protected Map<String, ParametroFormal> parametros;
    protected List<ParametroFormal> lista_parametros; //la lista es porque necesitamos el orden de los parametros

    public Unidad(){
        parametros = new HashMap<>();
        lista_parametros = new ArrayList<>();
    }


    public void insertarParametro(String nombreParametro, ParametroFormal parametro) throws ExcepcionSemantica{
        if(parametros.get(nombreParametro) == null){
            parametros.put(nombreParametro, parametro);
            lista_parametros.add(parametro);
        } else{
            throw new ExcepcionSemantica(parametro.getTokenIdVar(), "ya existe otro parametro con el mismo nombre");
        }
    }

    public List<ParametroFormal> getListaParametros(){
        return lista_parametros;
    }

    protected boolean mismosParametros(Metodo metodo2){
        List<ParametroFormal> lista_parametros_metodo2 = metodo2.getListaParametros();
        int i = 0;
        boolean mismosParametros = true;
        if(lista_parametros.size() == lista_parametros_metodo2.size()){
            for(ParametroFormal p1 : lista_parametros){
                ParametroFormal p2 = lista_parametros_metodo2.get(i);
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
}
