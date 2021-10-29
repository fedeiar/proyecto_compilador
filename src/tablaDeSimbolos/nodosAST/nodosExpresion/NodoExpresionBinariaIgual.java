package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionBinariaIgual extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaIgual(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        Tipo tipoExpresionLadoIzq = nodoExpresionLadoIzq.chequear();
        Tipo tipoExpresionLadoDer = nodoExpresionLadoDer.chequear();
        if(tipoExpresionLadoIzq.esSubtipo(tipoExpresionLadoDer) || tipoExpresionLadoDer.esSubtipo(tipoExpresionLadoIzq)){
            return new TipoBoolean();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador"+ tokenOperadorBinario.getLexema()+" solo funciona con tipos conformantes");
        }
    }
}
