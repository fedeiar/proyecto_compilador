package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.*;
public class NodoEntero extends NodoOperando{
    
    private Token tokenLitEntero;

    public NodoEntero(Token tokenLitEntero){
        this.tokenLitEntero = tokenLitEntero;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoInt();
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenLitEntero, "una literal no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenLitEntero, "una literal no es una llamada");
    }
}