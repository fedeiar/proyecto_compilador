package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public class NodoString extends NodoOperando{
    
    private Token tokenLitString;

    public NodoString(Token tokenLitString){
        this.tokenLitString = tokenLitString;
    }
}
