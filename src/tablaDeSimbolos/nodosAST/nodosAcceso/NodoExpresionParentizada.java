package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionParentizada extends NodoPrimario{

    private NodoExpresion nodoExpresion;

    public NodoExpresionParentizada(NodoExpresion nodoExpresion){
        this.nodoExpresion = nodoExpresion;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        return nodoExpresion.chequear();
    }

}
