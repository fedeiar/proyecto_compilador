package tablaDeSimbolos.nodosAST;

import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;

public abstract class NodoAsignacion extends NodoSentencia{
    
    protected NodoAcceso nodoAccesoLadoIzq;
    
    public NodoAsignacion(NodoAcceso nodoAccesoLadoIzq){
        this.nodoAccesoLadoIzq = nodoAccesoLadoIzq;
    }


}
