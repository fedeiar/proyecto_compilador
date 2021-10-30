package tablaDeSimbolos.nodosAST.nodosExpresion;
import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;

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

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenOperadorBinario, "una expresion binaria no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenOperadorBinario, "una expresion binaria no es una llamada");
    }
    
}
