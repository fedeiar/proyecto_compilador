package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;

public class NodoVarLocal extends NodoSentencia{
    
    private Token tokenIdVar;
    private Tipo tipo;
    private NodoExpresion nodoExpresion;

    public NodoVarLocal(Token tokenIdVar, Tipo tipo) throws ExcepcionSemantica{
        this.tokenIdVar = tokenIdVar;
        this.tipo = tipo;
        TablaSimbolos.getBloqueActual().insertarVarLocal(this); //TODO: esta bien?
    }

    public Token getToken(){
        return tokenIdVar;
    }

    public Tipo getTipo(){
        return tipo;
    }

    public void insertarExpresion(NodoExpresion nodoExpresion){
        this.nodoExpresion = nodoExpresion;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: seria solo eso lo que hay que chequear?
        if(nodoExpresion != null){
            if(!nodoExpresion.chequear().esSubtipo(tipo)){
                throw new ExcepcionSemantica(nodoExpresion.getToken(), "el tipo de la expresion no es compatible con el tipo de la declaracion de la variable local");
            }
        }
    }

    public String toString(){
        return tokenIdVar.getLexema();
    }
}
