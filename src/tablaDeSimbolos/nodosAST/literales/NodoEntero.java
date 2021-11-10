package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.*;
public class NodoEntero extends NodoOperando{
    
    private Token tokenLitEntero;

    public NodoEntero(Token tokenLitEntero){
        this.tokenLitEntero = tokenLitEntero;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoInt();
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: esta bien es solo esto?
        TablaSimbolos.instruccionesMaquina.add("PUSH "+tokenLitEntero.getLexema());
    }
}