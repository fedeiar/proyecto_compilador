package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;


public abstract class NodoAccesoUnidad extends NodoPrimario{
    
    protected List<NodoExpresion> listaParametrosActuales;

    public NodoAccesoUnidad(List<NodoExpresion> listaParametrosActuales){
        this.listaParametrosActuales = listaParametrosActuales;
    }

}
