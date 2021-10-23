package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.Tipo;

public class NodoVarLocal extends NodoSentencia{
    
    private Token tokenIdVar;
    private Tipo tipo;
    private NodoExpresion nodoExpresion;

    public NodoVarLocal(Token tokenIdVar, Tipo tipo){
        this.tokenIdVar = tokenIdVar;
        this.tipo = tipo;
    }

    public void insertarExpresion(NodoExpresion nodoExpresion){
        this.nodoExpresion = nodoExpresion;
    }
}
