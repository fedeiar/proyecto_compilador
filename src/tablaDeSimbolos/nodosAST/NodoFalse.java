package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoFalse extends NodoBoolean{
    
    private Token tokenFalse;

    public NodoFalse(Token tokenFalse){
        this.tokenFalse = tokenFalse;
    }
}
