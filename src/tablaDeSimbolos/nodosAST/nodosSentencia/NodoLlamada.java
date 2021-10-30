package tablaDeSimbolos.nodosAST.nodosSentencia;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;

public class NodoLlamada extends NodoSentencia{
    

    private NodoAcceso nodoAcceso;

    public NodoLlamada(NodoAcceso nodoAcceso){
        this.nodoAcceso = nodoAcceso;
    }

    public void chequear() throws ExcepcionSemantica{
        nodoAcceso.chequear();
        nodoAcceso.esLlamada();  
    }
}
