package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoEntero extends NodoOperando{
    
    private Token tokenLitEntero;

    public NodoEntero(Token tokenLitEntero){
        this.tokenLitEntero = tokenLitEntero;
    }
}
