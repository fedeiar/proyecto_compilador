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
}
