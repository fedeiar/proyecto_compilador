package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
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

    public boolean esAsignable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esAsignable();
        }else {
            return false;
        }
    }

    public boolean esLlamable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esLlamable();
        } else{
            return false;
        }
    }

}
