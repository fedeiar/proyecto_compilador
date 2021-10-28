package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionBinariaIgual extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaIgual(Token tokenOperadorBinario){
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
