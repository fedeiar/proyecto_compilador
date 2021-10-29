package tablaDeSimbolos.nodosAST.nodosExpresion;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoMetodo;

public abstract class NodoExpresion {
 
    public abstract TipoMetodo chequear() throws ExcepcionSemantica;
}
