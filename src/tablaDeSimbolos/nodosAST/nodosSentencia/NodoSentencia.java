package tablaDeSimbolos.nodosAST.nodosSentencia;

import tablaDeSimbolos.ExcepcionSemantica;

public abstract class NodoSentencia {
    
    public abstract void chequear() throws ExcepcionSemantica;
}
