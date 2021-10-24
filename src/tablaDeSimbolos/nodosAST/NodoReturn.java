package tablaDeSimbolos.nodosAST;

public class NodoReturn extends NodoSentencia{
    
    private NodoExpresion nodoExpresionRetorno;

    public NodoReturn(){

    }

    public void insertarExpresion(NodoExpresion nodoExpresionRetorno){
        this.nodoExpresionRetorno = nodoExpresionRetorno;
    }
}
