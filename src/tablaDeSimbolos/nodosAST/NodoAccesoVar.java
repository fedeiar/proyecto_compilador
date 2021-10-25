package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;

public class NodoAccesoVar extends NodoPrimario{
    
    private Token tokenIdVar;

    public NodoAccesoVar(Token tokenIdVar){
        this.tokenIdVar = tokenIdVar;
    }


    public Tipo chequear() throws ExcepcionSemantica{ //TODO: va a hacer falta el bloque actual no?

    }
}
