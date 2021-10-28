package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.ExcepcionSemantica;

public class NodoExpresionBinariaNotIgual extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaNotIgual(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().esSubtipo(nodoExpresionLadoDer.chequear()) || nodoExpresionLadoDer.chequear().esSubtipo(nodoExpresionLadoIzq.chequear())){
            return new TipoBoolean();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador"+ tokenOperadorBinario.getLexema()+" solo funciona con tipos conformantes");
        }
    }


}
