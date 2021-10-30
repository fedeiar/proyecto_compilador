package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoConcreto;

public class NodoVarLocal extends NodoSentencia{
    
    private Token tokenIdVar;
    private TipoConcreto tipoVarLocal;
    private Token tokenIgual;
    private NodoExpresion nodoExpresion;

    public NodoVarLocal(Token tokenIdVar, TipoConcreto tipo){
        this.tokenIdVar = tokenIdVar;
        this.tipoVarLocal = tipo;
    }

    public Token getToken(){
        return tokenIdVar;
    }

    public TipoConcreto getTipo(){
        return tipoVarLocal;
    }

    public void insertarExpresion(Token tokenIgual, NodoExpresion nodoExpresion){
        this.tokenIgual = tokenIgual;
        this.nodoExpresion = nodoExpresion;
    }

    public void chequear() throws ExcepcionSemantica{ 
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
