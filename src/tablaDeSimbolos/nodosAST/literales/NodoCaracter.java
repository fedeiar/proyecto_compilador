package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public class NodoCaracter extends NodoOperando{
    
    private Token tokenLitCaracter;

    public NodoCaracter(Token tokenLitCaracter){
        this.tokenLitCaracter = tokenLitCaracter;
    }
}
