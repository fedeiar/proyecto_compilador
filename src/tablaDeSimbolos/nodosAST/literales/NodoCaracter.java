package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.*;
public class NodoCaracter extends NodoOperando{
    
    private Token tokenLitCaracter;

    public NodoCaracter(Token tokenLitCaracter){
        this.tokenLitCaracter = tokenLitCaracter;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoChar();
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenLitCaracter, "una literal no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenLitCaracter, "una literal no es una llamada");
    }
}
