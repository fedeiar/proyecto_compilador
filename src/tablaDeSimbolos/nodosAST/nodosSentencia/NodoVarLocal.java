package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.entidades.IVariable;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoConcreto;

public class NodoVarLocal extends NodoSentencia implements IVariable{
    
    private Token tokenIdVar;
    private TipoConcreto tipoVarLocal;
    private Token tokenIgual;
    private NodoExpresion nodoExpresion;

    private int offset; // TODO: esta bien?

    public NodoVarLocal(Token tokenIdVar, TipoConcreto tipo){
        this.tokenIdVar = tokenIdVar;
        this.tipoVarLocal = tipo;

        this.offset = 1; // Inicialmente no tiene offset. Es un numero positivo ya que las varLocales comienzan en 0 y se desplazan hacia abajo.
    }

    public Token getToken(){
        return tokenIdVar;
    }

    public TipoConcreto getTipo(){
        return tipoVarLocal;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset = offset;
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
                throw new ExcepcionSemantica(tokenIgual, "el tipo de la expresion no conforma con el tipo de la declaracion de la variable local");
            }
        }
    }

    public String toString(){
        return tokenIdVar.getLexema();
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: esta bien?
        TablaSimbolos.instruccionesMaquina.add("RMEM 1 ; Reservamos un lugar para declaracion de variable local");
        if(nodoExpresion != null){
            nodoExpresion.generarCodigo();
            TablaSimbolos.instruccionesMaquina.add("STORE "+this.getOffset()+" ; almaceno el valor de la expresion en la variable local"); 
        }
    }
    
}
