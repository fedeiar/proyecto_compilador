package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoExpresionParentizada extends NodoPrimario{

    private NodoExpresion nodoExpresion;

    public NodoExpresionParentizada(NodoExpresion nodoExpresion){
        this.nodoExpresion = nodoExpresion;
    }

    public Token getToken(){ //TODO: esta bien asi?
        return nodoExpresion.getToken();
    }
}
