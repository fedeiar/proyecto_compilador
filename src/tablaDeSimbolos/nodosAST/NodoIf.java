package tablaDeSimbolos.nodosAST;

public class NodoIf extends NodoSentencia{
    
    private NodoExpresion NodoExpresionCondicion;
    private NodoSentencia nodoSentenciaIf;
    private NodoSentencia nodoSentenciaElse;

    public NodoIf(NodoExpresion condicion, NodoSentencia sentenciaIf){
        this.NodoExpresionCondicion = condicion;
        this.nodoSentenciaIf = sentenciaIf;
    }


    public void insertarSentenciaElse(NodoSentencia nodoSentenciaElse){
        nodoSentenciaElse = this.nodoSentenciaElse;
    }
}
