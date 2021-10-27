package tablaDeSimbolos.nodosAST;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoEncadenado {
    
    protected NodoEncadenado nodoEncadenado;

    public void insertarNodoEncadenado(NodoEncadenado nodoEncadenado){
        this.nodoEncadenado = nodoEncadenado;
    }

    public abstract Tipo chequear(Tipo tipo) throws ExcepcionSemantica;
}
