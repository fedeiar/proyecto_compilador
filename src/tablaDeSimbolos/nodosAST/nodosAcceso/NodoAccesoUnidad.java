package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import java.util.ArrayList;

public abstract class NodoAccesoUnidad extends NodoPrimario{
    
    protected List<NodoExpresion> listaParametrosActuales;

    public NodoAccesoUnidad(List<NodoExpresion> listaParametrosActuales){
        this.listaParametrosActuales = listaParametrosActuales;
    }

    public static List<Tipo> getListaTipos(List<NodoExpresion> listaParametrosActuales) throws ExcepcionSemantica{
        List<Tipo> listaTiposParametrosActuales = new ArrayList<>();
        for(NodoExpresion parametroActual : listaParametrosActuales){
            listaTiposParametrosActuales.add(parametroActual.chequear());
        }
        return listaTiposParametrosActuales;
    }

}
