package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoEncadenado {
    
    protected NodoEncadenado nodoEncadenado;
    protected boolean esLadoIzquierdoAsignacion = false; // Por default, el encadenado es una expresión (es decir, un lado derecho)

    public void insertarNodoEncadenado(NodoEncadenado nodoEncadenado){
        this.nodoEncadenado = nodoEncadenado;
    }

    public void establecerMismoLado(boolean esLadoIzquierdo){
        this.esLadoIzquierdoAsignacion = esLadoIzquierdo;
    }

    public abstract Tipo chequear(Tipo tipo) throws ExcepcionSemantica;

    public abstract Tipo chequearThis(Tipo tipo) throws ExcepcionSemantica; 

    public abstract boolean esAsignable();

    public abstract boolean esLlamable();

    public abstract void generarCodigo();

    public abstract void generarCodigoSuper();

}
