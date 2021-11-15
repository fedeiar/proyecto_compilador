package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public abstract class NodoAcceso extends NodoOperando{

    protected boolean esLadoIzquierdoAsignacion = false; //TODO: asi esta bien?
    
    public abstract boolean esAsignable();

    public abstract boolean esLlamable();


    public void establecerComoLadoIzquierdo(){ //TODO: asi esta bien?
        esLadoIzquierdoAsignacion = true;
    }

    public void establecerMismoLado(boolean esLadoIzquierdo){
        esLadoIzquierdoAsignacion = esLadoIzquierdo;
    }
    
}
