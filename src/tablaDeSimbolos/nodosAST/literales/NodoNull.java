package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.*;

public class NodoNull extends NodoOperando{

    private Token tokenNull;

    public NodoNull(Token tokenNull){
        this.tokenNull = tokenNull;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoNull(); 
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenNull, "una literal no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenNull, "una literal no es una llamada");
    }
}
