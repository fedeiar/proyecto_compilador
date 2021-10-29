package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoString;

public class NodoString extends NodoOperando{
    
    private Token tokenLitString;

    public NodoString(Token tokenLitString){
        this.tokenLitString = tokenLitString;
    }

    public Tipo chequear() throws ExcepcionSemantica{
        return new TipoString();
    }
}
