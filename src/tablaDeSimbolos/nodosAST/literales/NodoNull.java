package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public class NodoNull extends NodoOperando{

    private Token tokenNull;

    public NodoNull(Token tokenNull){
        this.tokenNull = tokenNull;
    }
}
