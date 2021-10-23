package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoString extends NodoOperando{
    
    private Token tokenLitString;

    public NodoString(Token tokenLitString){
        this.tokenLitString = tokenLitString;
    }
}
