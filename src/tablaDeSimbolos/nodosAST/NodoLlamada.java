package tablaDeSimbolos.nodosAST;

public class NodoLlamada extends NodoSentencia{
    

    private NodoAcceso nodoAcceso;

    public NodoLlamada(NodoAcceso nodoAcceso){
        this.nodoAcceso = nodoAcceso;
    }
}
