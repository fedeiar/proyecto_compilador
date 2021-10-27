package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;

public class NodoFalse extends NodoBoolean{
    
    private Token tokenFalse;

    public NodoFalse(Token tokenFalse){
        this.tokenFalse = tokenFalse;
    }
}
