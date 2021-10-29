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

    public Tipo chequear() throws ExcepcionSemantica{
        return new TipoInt();
    }
}