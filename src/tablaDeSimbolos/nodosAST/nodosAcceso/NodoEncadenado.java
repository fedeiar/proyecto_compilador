package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoMetodo;

public abstract class NodoEncadenado {
    
    protected NodoEncadenado nodoEncadenado;

    public void insertarNodoEncadenado(NodoEncadenado nodoEncadenado){
        this.nodoEncadenado = nodoEncadenado;
    }

    public abstract TipoMetodo chequear(TipoMetodo tipo) throws ExcepcionSemantica;
}
