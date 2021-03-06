package tablaDeSimbolos.nodosAST.nodosExpresion;
import analizadorLexico.Token;

public abstract class NodoExpresionBinaria extends NodoExpresion{
    
    protected Token tokenOperadorBinario;
    protected NodoExpresion nodoExpresionLadoIzq;
    protected NodoExpresion nodoExpresionLadoDer;
    
    public NodoExpresionBinaria(Token tokenOperadorBinario){
        this.tokenOperadorBinario = tokenOperadorBinario;
    }

    public void setExpresiones(NodoExpresion nodoLadoIzq, NodoExpresion nodoLadoDer){
        this.nodoExpresionLadoIzq = nodoLadoIzq;
        this.nodoExpresionLadoDer = nodoLadoDer;
    }
    
   
}
