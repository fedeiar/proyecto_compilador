package tablaDeSimbolos.entidades;

import tablaDeSimbolos.nodosAST.nodosSentencia.NodoBloque;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoVarLocal;
import tablaDeSimbolos.tipos.Tipo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class Unidad {
    
    protected Map<String, ParametroFormal> parametros;
    protected List<ParametroFormal> lista_parametrosFormales; // La lista es porque necesitamos el orden de los parametros

    protected boolean esDinamico;
    protected NodoBloque bloque;
    protected Tipo tipoUnidad;

    protected int offsetDisponibleParametroFormal; 
    protected int offsetDisponibleVariableLocal; // Manejando las variables locales aca, tenemos un manejo ineficiente de memoria ya que se liberan recien al salir del metodo. Considerar tenerlo mejor en bloque

    public Unidad(){
        parametros = new HashMap<>();
        lista_parametrosFormales = new ArrayList<>();
        bloque = new NodoBloque();

        // El offsetDisponibleParametroFormal se asigna en Metodo y Constructor segun corresponda.
        this.offsetDisponibleVariableLocal = 0; // comienza en 0 y luego se desplaza hacia abajo (es decir, se decrementa).
    }

    public List<ParametroFormal> getListaParametros(){
        return lista_parametrosFormales;
    }

    public Tipo getTipoUnidad(){ 
        return tipoUnidad;
    }

    public boolean esDinamico(){
        return esDinamico;
    }

    public int getOffsetRetornoUnidad(){ //TODO: esta bien?
        if(this.esDinamico){
            return lista_parametrosFormales.size() + 1;
        } else{
            return lista_parametrosFormales.size();
        }
    }

    // Debe ejecutarse despues de hacer FMEM
    public int getOffsetStoreValorRetorno(){ 
        if(esDinamico){
            return 3 + lista_parametrosFormales.size() + 1; // +3 ya que tiene la ED, el PR y this. +1 ya que FP esta apuntando a la primer varLocal
        } else{
            return 2 + lista_parametrosFormales.size() + 1; // +2 ya que no tiene this.
        }
    }

    public int getCantVarLocalesALiberar(){
        return offsetDisponibleVariableLocal * -1;
    }

    public void agregarOffsetVarLocal(NodoVarLocal nodoVarLocal){
        nodoVarLocal.setOffset(offsetDisponibleVariableLocal);
        offsetDisponibleVariableLocal--;
    }

    public void insertarParametro(ParametroFormal parametro) throws ExcepcionSemantica{
        if(parametros.get(parametro.toString()) == null){
            parametros.put(parametro.toString(), parametro);
            lista_parametrosFormales.add(parametro);
        } else{
            throw new ExcepcionSemantica(parametro.getTokenIdVar(), "ya existe otro parametro con el mismo nombre en la unidad");
        }
    }

    public ParametroFormal getParametroFormal(String nombreParametroFormal){
        return parametros.get(nombreParametroFormal);
    }

    public ParametroFormal getParametroFormal(int pos){
        return lista_parametrosFormales.get(pos);
    }

    public boolean mismosParametros(Unidad unidad2){
        List<ParametroFormal> lista_parametrosFormales_unidad2 = unidad2.getListaParametros();
        int i = 0;
        boolean mismosParametros = true;
        if(lista_parametrosFormales.size() == lista_parametrosFormales_unidad2.size()){
            for(ParametroFormal p1 : lista_parametrosFormales){
                ParametroFormal p2 = lista_parametrosFormales_unidad2.get(i);
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

    public boolean conformanParametros(List<Tipo> listaTiposParametrosActuales){
        List<Tipo> listaTiposParametrosFormales = new ArrayList<>();
        for(ParametroFormal parametroFormal : lista_parametrosFormales){
            listaTiposParametrosFormales.add(parametroFormal.getTipo());
        }

        int i = 0;
        boolean parametrosConformantes = true;
        if(listaTiposParametrosFormales.size() == listaTiposParametrosActuales.size()){
            for(Tipo tipoFormal : listaTiposParametrosFormales){
                Tipo tipoActual = listaTiposParametrosActuales.get(i);
                i++;
                if(!tipoActual.esSubtipo(tipoFormal)){
                    
                    parametrosConformantes = false;
                    break;
                }
            }
        } else{
            parametrosConformantes = false;
        }
        return parametrosConformantes;
    }

    // Chequeo de declaraciones

    public void estaBienDeclarado() throws ExcepcionSemantica{
        // Recorremos la lista en orden inverso ya que el ultimo parametro es el ultimo en apilarse, por lo tanto tendra el menor offset
        for(int i = lista_parametrosFormales.size() - 1; i > -1; i--){ 
            ParametroFormal parametro = lista_parametrosFormales.get(i);
            parametro.estaBienDeclarado();
            agregarOffsetParametro(parametro);
        }
    }

    private void agregarOffsetParametro(ParametroFormal parametroFormal){
        parametroFormal.setOffset(offsetDisponibleParametroFormal);
        offsetDisponibleParametroFormal++;
    }

    public void insertarBloque(NodoBloque bloque){ 
        this.bloque = bloque;
    }

    // Chequeo de sentencias

    public void chequearSentencias() throws ExcepcionSemantica{
        TablaSimbolos.unidadActual = this;
        bloque.chequear();
    }

    // Generacion de codigo intermedio

    public abstract void generarCodigo();
    
}
