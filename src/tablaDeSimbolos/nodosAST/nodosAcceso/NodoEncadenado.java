package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoEncadenado {
    
    protected NodoEncadenado nodoEncadenado;

    public void insertarNodoEncadenado(NodoEncadenado nodoEncadenado){
        this.nodoEncadenado = nodoEncadenado;
    }

    public abstract Tipo chequear(Tipo tipo) throws ExcepcionSemantica;

    public abstract Tipo chequearThis(Tipo tipo) throws ExcepcionSemantica; 

    public abstract boolean esAsignable();

    public abstract boolean esLlamable();

    public abstract void generarCodigo(); //TODO: esta bien?
}
