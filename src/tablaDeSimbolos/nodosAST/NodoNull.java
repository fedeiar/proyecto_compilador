package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoNull extends NodoOperando{

    private Token tokenNull;

    public NodoNull(Token tokenNull){
        this.tokenNull = tokenNull;
    }
}
