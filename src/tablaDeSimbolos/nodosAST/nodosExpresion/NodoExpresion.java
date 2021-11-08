package tablaDeSimbolos.nodosAST.nodosExpresion;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoExpresion {
 
    public abstract Tipo chequear() throws ExcepcionSemantica;

    public abstract void generarCodigo(); // TODO: esta bien?

}
