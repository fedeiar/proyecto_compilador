package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoReturn extends NodoSentencia{
    
    private NodoExpresion nodoExpresionRetorno;
    private Token tokenReturn;

    public NodoReturn(Token tokenReturn){
        this.tokenReturn = tokenReturn;
    }

    public void insertarExpresion(NodoExpresion nodoExpresionRetorno){
        this.nodoExpresionRetorno = nodoExpresionRetorno;
    }
}
