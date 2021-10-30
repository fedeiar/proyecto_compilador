package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.ExcepcionSemantica;

public class NodoTrue extends NodoBoolean{
    
    private Token tokenTrue;

    public NodoTrue(Token tokenTrue){
        this.tokenTrue = tokenTrue;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoBoolean();
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenTrue, "una literal no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenTrue, "una literal no es una llamada");
    }
}
