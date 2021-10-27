package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;

public class NodoExpresionParentizada extends NodoPrimario{

    private NodoExpresion nodoExpresion;

    public NodoExpresionParentizada(NodoExpresion nodoExpresion){
        this.nodoExpresion = nodoExpresion;
    }

}
