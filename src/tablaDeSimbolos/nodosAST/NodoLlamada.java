package tablaDeSimbolos.nodosAST;

import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;

public class NodoLlamada extends NodoSentencia{
    

    private NodoAcceso nodoAcceso;

    public NodoLlamada(NodoAcceso nodoAcceso){
        this.nodoAcceso = nodoAcceso;
    }
}
