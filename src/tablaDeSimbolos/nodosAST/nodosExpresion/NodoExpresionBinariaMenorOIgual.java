package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.ExcepcionSemantica;

public class NodoExpresionBinariaMenorOIgual extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaMenorOIgual(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().mismoTipo(new TipoInt()) && nodoExpresionLadoDer.chequear().mismoTipo(new TipoInt())){
            return new TipoBoolean();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador"+ tokenOperadorBinario.getLexema()+" solo funciona con tipos enteros");
        }
    }
}
