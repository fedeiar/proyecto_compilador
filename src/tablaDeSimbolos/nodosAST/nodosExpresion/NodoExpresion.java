package tablaDeSimbolos.nodosAST.nodosExpresion;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoExpresion {
 
    public abstract Tipo chequear() throws ExcepcionSemantica;

    public abstract void esVariable() throws ExcepcionSemantica; //TODO: esta bien declarado aca? tendría que ser void o boolean?

    public abstract void esLlamada() throws ExcepcionSemantica; //TODO: esta bien declarado aca?
}
