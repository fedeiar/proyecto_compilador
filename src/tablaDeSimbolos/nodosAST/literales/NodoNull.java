package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
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
}
