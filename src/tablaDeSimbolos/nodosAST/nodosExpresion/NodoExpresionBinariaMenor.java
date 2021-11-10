package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionBinariaMenor extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaMenor(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().mismoTipo(new TipoInt()) && nodoExpresionLadoDer.chequear().mismoTipo(new TipoInt())){
            return new TipoBoolean();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador binario "+ tokenOperadorBinario.getLexema()+" solo funciona con tipos enteros");
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: esta bien asi?
        nodoExpresionLadoIzq.generarCodigo();
        nodoExpresionLadoDer.generarCodigo();
        TablaSimbolos.instruccionesMaquina.add("PUSH LT");
    }
}
