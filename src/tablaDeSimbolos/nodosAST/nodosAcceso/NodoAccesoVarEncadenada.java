package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;

public class NodoAccesoVarEncadenada extends NodoEncadenado{
    
    protected Token tokenIdVar;

    public NodoAccesoVarEncadenada(Token tokenIdVar){
        this.tokenIdVar = tokenIdVar;
    }
}
