package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public abstract class NodoAcceso extends NodoOperando{

    protected boolean esLadoIzquierdoAsignacion = false;

    public void establecerComoLadoIzquierdo(){
        esLadoIzquierdoAsignacion = true;
    }

    public void establecerMismoLado(boolean esLadoIzquierdo){
        esLadoIzquierdoAsignacion = esLadoIzquierdo;
    }
    
    public abstract boolean esAsignable();

    public abstract boolean esLlamable();



    
}
