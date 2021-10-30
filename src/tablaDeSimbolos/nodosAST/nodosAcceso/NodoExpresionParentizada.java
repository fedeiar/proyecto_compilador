package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionParentizada extends NodoPrimario{

    private NodoExpresion nodoExpresion;

    public NodoExpresionParentizada(NodoExpresion nodoExpresion){
        this.nodoExpresion = nodoExpresion;
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(nodoExpresion.chequear());
        } else{
            return nodoExpresion.chequear();
        }
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        if(nodoEncadenado != null){
            nodoEncadenado.esVariable();
        }else {
            nodoExpresion.esVariable();
        }
    }

    public void esLlamada() throws ExcepcionSemantica{ //TODO: esta bien?
        if(nodoEncadenado != null){
            nodoEncadenado.esLlamada();
        } else{
            nodoExpresion.esLlamada(); //TODO: ESTO ESTA BIEEEN?
        }
    }

}
