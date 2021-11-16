package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.TipoConcreto;
import tablaDeSimbolos.tipos.TipoInt;

public class NodoExpresionBinariaSuma extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaSuma(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().mismoTipo(new TipoInt()) && nodoExpresionLadoDer.chequear().mismoTipo(new TipoInt())){
            return new TipoInt();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador binario "+ tokenOperadorBinario.getLexema()+" solo funciona con tipos enteros");
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoExpresionLadoIzq.generarCodigo();
        nodoExpresionLadoDer.generarCodigo();
        TablaSimbolos.listaInstruccionesMaquina.add("ADD");
    }
}
