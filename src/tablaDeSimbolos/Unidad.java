package tablaDeSimbolos;
import java.util.List;
import java.util.Map;

public abstract class Unidad {
    
    protected Map<String, ParametroFormal> parametros;
    protected List<ParametroFormal> lista_parametros; //la lista es porque necesitamos el orden de los parametros

    public void insertarParametro(String nombreParametro, ParametroFormal parametro) throws ExcepcionSemantica{
        if(parametros.get(nombreParametro) == null){
            parametros.put(nombreParametro, parametro);
            lista_parametros.add(parametro);
        } else{
            throw new ExcepcionSemantica(parametro.getTokenIdVar());
        }
    }

    public List<ParametroFormal> getListaParametros(){
        return lista_parametros;
    }

    protected boolean mismosParametros(Metodo metodo2){
        List<ParametroFormal> lista_parametros_metodo2 = metodo2.getListaParametros();
        int i = 0;
        boolean mismosParametros = true;
        for(ParametroFormal p1 : lista_parametros){
            ParametroFormal p2 = lista_parametros_metodo2.get(i);
            i++;
            if(!p1.mismoTipo(p2)){
                mismosParametros = false;
                break;
            }
        }
        return mismosParametros;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica{
        for(ParametroFormal p : parametros.values()){
            p.estaBienDeclarado();
        }
    }
}
