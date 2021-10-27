package tablaDeSimbolos.nodosAST.nodosExpresion;
import analizadorLexico.Token;

public class NodoExpresionBinaria extends NodoExpresion{
    
    private Token tokenOperadorBinario;
    private NodoExpresion nodoExpresionLadoIzq;
    private NodoExpresion nodoExpresionLadoDer;
    
    public NodoExpresionBinaria(Token tokenOperadorBinario, NodoExpresion nodoLadoIzq, NodoExpresion nodoLadoDer){
        this.tokenOperadorBinario = tokenOperadorBinario;
        this.nodoExpresionLadoIzq = nodoLadoIzq;
        this.nodoExpresionLadoDer = nodoLadoDer;
    }
    
}
