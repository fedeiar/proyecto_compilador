package tablaDeSimbolos.nodosAST;

public class NodoAsignacionExpresion extends NodoAsignacion{
    
    private NodoExpresion nodoExpresionLadoDer;

    public NodoAsignacionExpresion(NodoAcceso nodoAccesoLadoIzq, NodoExpresion nodoExpresionLadoDer){
        super(nodoAccesoLadoIzq);
        this.nodoExpresionLadoDer = nodoExpresionLadoDer;
    }
    
}
