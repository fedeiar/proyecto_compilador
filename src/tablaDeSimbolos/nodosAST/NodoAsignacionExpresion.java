package tablaDeSimbolos.nodosAST;

import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;

public class NodoAsignacionExpresion extends NodoAsignacion{
    
    private NodoExpresion nodoExpresionLadoDer;

    public NodoAsignacionExpresion(NodoAcceso nodoAccesoLadoIzq, NodoExpresion nodoExpresionLadoDer){
        super(nodoAccesoLadoIzq);
        this.nodoExpresionLadoDer = nodoExpresionLadoDer;
    }
    
}
