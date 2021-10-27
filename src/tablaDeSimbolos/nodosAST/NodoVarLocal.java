package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.Tipo;

public class NodoVarLocal extends NodoSentencia{
    
    private Token tokenIdVar;
    private Tipo tipo;
    private Token tokenIgual;
    private NodoExpresion nodoExpresion;

    public NodoVarLocal(Token tokenIdVar, Tipo tipo){
        this.tokenIdVar = tokenIdVar;
        this.tipo = tipo;
    }

    public Token getToken(){
        return tokenIdVar;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public void insertarExpresion(Token tokenIgual, NodoExpresion nodoExpresion){
        this.tokenIgual = tokenIgual;
        this.nodoExpresion = nodoExpresion;
    }

    public void chequear() throws ExcepcionSemantica{ 
        TablaSimbolos.getBloqueActual().insertarVarLocal(this); 
        tipo.verificarExistenciaTipo();
        
        if(nodoExpresion != null){
            if(!nodoExpresion.chequear().esSubtipo(tipo)){
                throw new ExcepcionSemantica(tokenIgual, "el tipo de la expresion no es compatible con el tipo de la declaracion de la variable local");
            }
        }
    }

    public String toString(){
        return tokenIdVar.getLexema();
    }
}
