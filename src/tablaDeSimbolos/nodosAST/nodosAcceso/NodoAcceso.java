package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public abstract class NodoAcceso extends NodoOperando{

    protected boolean esLadoIzquierdoAsignacion = false; // Por default, el acceso es una expresión (es decir, un lado derecho)

    public void establecerComoLadoIzquierdo(){
        esLadoIzquierdoAsignacion = true;
    }

    public void establecerMismoLado(boolean esLadoIzquierdo){
        this.esLadoIzquierdoAsignacion = esLadoIzquierdo;
    }
    
    public abstract boolean esAsignable();

    public abstract boolean esLlamable();
    
}
