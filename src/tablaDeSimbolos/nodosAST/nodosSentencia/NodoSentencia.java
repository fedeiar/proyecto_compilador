package tablaDeSimbolos.nodosAST.nodosSentencia;

import tablaDeSimbolos.entidades.ExcepcionSemantica;

public abstract class NodoSentencia {
    
    public abstract void chequear() throws ExcepcionSemantica;
}
