package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoCaracter extends NodoOperando{
    
    private Token tokenLitCaracter;

    public NodoCaracter(Token tokenLitCaracter){
        this.tokenLitCaracter = tokenLitCaracter;
    }
}
