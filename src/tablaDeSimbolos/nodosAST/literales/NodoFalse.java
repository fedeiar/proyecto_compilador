package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.TipoBoolean;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.TipoConcreto;

public class NodoFalse extends NodoBoolean{
    
    private Token tokenFalse;

    public NodoFalse(Token tokenFalse){
        this.tokenFalse = tokenFalse;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoBoolean();
    }
}
