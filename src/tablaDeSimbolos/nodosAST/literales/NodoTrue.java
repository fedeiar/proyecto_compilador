package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;

public class NodoTrue extends NodoBoolean{
    
    private Token tokenTrue;

    public NodoTrue(Token tokenTrue){
        this.tokenTrue = tokenTrue;
    }
}
