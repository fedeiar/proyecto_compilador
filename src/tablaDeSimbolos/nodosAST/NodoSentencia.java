package tablaDeSimbolos.nodosAST;

import tablaDeSimbolos.ExcepcionSemantica;

public abstract class NodoSentencia {
    

    public abstract void chequear() throws ExcepcionSemantica;
}
