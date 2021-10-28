package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.ExcepcionSemantica;

public class NodoExpresionBinariaMayorOIgual extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaMayorOIgual(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().visitarMismoTipo(new TipoInt()) && nodoExpresionLadoDer.chequear().visitarMismoTipo(new TipoInt())){
            return new TipoBoolean();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador"+ tokenOperadorBinario.getLexema()+" solo funciona con tipos enteros");
        }
    }
}
