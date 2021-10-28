package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoInt;

public class NodoExpresionBinariaSuma extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaSuma(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().visitarMismoTipo(new TipoInt()) && nodoExpresionLadoDer.chequear().visitarMismoTipo(new TipoInt())){
            return new TipoInt();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador"+ tokenOperadorBinario.getLexema()+" solo funciona con tipos enteros");
        }
    }
}
