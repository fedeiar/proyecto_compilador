package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoThis extends NodoPrimario{

    private Token tokenThis;

    public NodoThis(Token tokenThis){
        this.tokenThis = tokenThis;
    }
    
}
