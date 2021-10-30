package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.tipos.TipoBoolean;
import tablaDeSimbolos.tipos.TipoConcreto;

public class NodoFalse extends NodoBoolean{
    
    private Token tokenFalse;

    public NodoFalse(Token tokenFalse){
        this.tokenFalse = tokenFalse;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoBoolean();
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenFalse, "una literal no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenFalse, "una literal no es una llamada");
    }
}
