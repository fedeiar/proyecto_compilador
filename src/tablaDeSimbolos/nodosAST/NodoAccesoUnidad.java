package tablaDeSimbolos.nodosAST;

import java.util.List;

public abstract class NodoAccesoUnidad extends NodoPrimario{
    
    protected List<NodoExpresion> listaParametrosActuales;

    public NodoAccesoUnidad(List<NodoExpresion> listaParametrosActuales){
        this.listaParametrosActuales = listaParametrosActuales;
    }
}
