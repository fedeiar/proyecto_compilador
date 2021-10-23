package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoExpresionUnaria extends NodoExpresion{
    
    private Token tokenOperador;
    private NodoOperando nodoOperando;
    
    public NodoExpresionUnaria(Token tokenOperador, NodoOperando nodoOperando){
        this.tokenOperador = tokenOperador;
        this.nodoOperando = nodoOperando;
    }

}
