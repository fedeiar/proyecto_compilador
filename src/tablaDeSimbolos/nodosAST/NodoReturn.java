package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;

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
