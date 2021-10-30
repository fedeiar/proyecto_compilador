package tablaDeSimbolos.nodosAST.nodosExpresion;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoExpresion {
 
    public abstract Tipo chequear() throws ExcepcionSemantica;

    public abstract void esVariable() throws ExcepcionSemantica; //TODO: esta bien declarado aca?

    public abstract void esLlamada() throws ExcepcionSemantica; //TODO: esta bien declarado aca?
}
