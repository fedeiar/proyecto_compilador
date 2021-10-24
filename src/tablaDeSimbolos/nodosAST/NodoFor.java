package tablaDeSimbolos.nodosAST;

public class NodoFor extends NodoSentencia{
    
    private NodoVarLocal nodoVarLocal;
    private NodoExpresion nodoExpresion;
    private NodoAsignacion nodoAsignacion;
    private NodoSentencia nodoSentencia;

    public NodoFor(NodoVarLocal nodoVarLocal, NodoExpresion nodoExpresion, NodoAsignacion nodoAsignacion, NodoSentencia nodoSentencia){
        this.nodoVarLocal = nodoVarLocal;
        this.nodoExpresion = nodoExpresion;
        this.nodoAsignacion = nodoAsignacion;
        this.nodoSentencia = nodoSentencia;
    }
}
