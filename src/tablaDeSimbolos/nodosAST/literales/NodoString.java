package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.TipoConcreto;
import tablaDeSimbolos.tipos.TipoString;

public class NodoString extends NodoOperando{
    
    private Token tokenLitString;

    public NodoString(Token tokenLitString){
        this.tokenLitString = tokenLitString;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoString();
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenLitString, "una literal no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenLitString, "una literal no es una llamada");
    }
}
