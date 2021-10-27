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

    public void chequear() throws ExcepcionSemantica{
        if(nodoExpresion != null){
            if(nodoExpresion.getTipo().verificarCompatibilidad(tipo)){
                
            }
        }
    }

    public String toString(){
        return tokenIdVar.getLexema();
    }
}
