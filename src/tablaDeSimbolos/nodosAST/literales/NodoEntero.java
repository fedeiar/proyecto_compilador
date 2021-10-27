package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public class NodoEntero extends NodoOperando{
    
    private Token tokenLitEntero;

    public NodoEntero(Token tokenLitEntero){
        this.tokenLitEntero = tokenLitEntero;
    }
}
