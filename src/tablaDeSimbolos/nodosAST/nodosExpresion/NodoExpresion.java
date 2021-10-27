package tablaDeSimbolos.nodosAST.nodosExpresion;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoExpresion {
 
    public abstract Tipo chequear() throws ExcepcionSemantica;
}
