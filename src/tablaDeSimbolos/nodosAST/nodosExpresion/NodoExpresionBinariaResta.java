package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionBinariaResta extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaResta(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().mismoTipo(new TipoInt()) && nodoExpresionLadoDer.chequear().mismoTipo(new TipoInt())){
            return new TipoInt();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador"+ tokenOperadorBinario.getLexema()+" solo funciona con tipos enteros");
        }
    }
}
