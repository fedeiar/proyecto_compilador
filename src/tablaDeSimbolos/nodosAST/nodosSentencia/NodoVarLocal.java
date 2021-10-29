package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.Tipo;

public class NodoVarLocal extends NodoSentencia{
    
    private Token tokenIdVar;
    private Tipo tipoVarLocal;
    private Token tokenIgual;
    private NodoExpresion nodoExpresion;

    public NodoVarLocal(Token tokenIdVar, Tipo tipo){
        this.tokenIdVar = tokenIdVar;
        this.tipoVarLocal = tipo;
    }

    public Token getToken(){
        return tokenIdVar;
    }

    public Tipo getTipo(){
        return tipoVarLocal;
    }

    public void insertarExpresion(Token tokenIgual, NodoExpresion nodoExpresion){
        this.tokenIgual = tokenIgual;
        this.nodoExpresion = nodoExpresion;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: ademas de el control de subtipo, habría que controlar que si el tipo de la varLocal es de tipo clase, la expresion puede ser null?
        tipoVarLocal.verificarExistenciaTipo();

        TablaSimbolos.getBloqueActual().insertarVarLocal(this); 
        
        if(nodoExpresion != null){
            if(!nodoExpresion.chequear().esSubtipo(tipoVarLocal)){
                throw new ExcepcionSemantica(tokenIgual, "el tipo de la expresion no es compatible con el tipo de la declaracion de la variable local");
            }
        }
    }

    public String toString(){
        return tokenIdVar.getLexema();
    }
}
