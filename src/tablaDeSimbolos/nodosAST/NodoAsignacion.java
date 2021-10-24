package tablaDeSimbolos.nodosAST;

public abstract class NodoAsignacion extends NodoSentencia{
    
    protected NodoAcceso nodoAccesoLadoIzq;
    
    public NodoAsignacion(NodoAcceso nodoAccesoLadoIzq){
        this.nodoAccesoLadoIzq = nodoAccesoLadoIzq;
    }


}
