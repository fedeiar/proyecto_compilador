package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;

public class NodoFor extends NodoSentencia{
    
    private Token tokenFor; //TODO: ponerla en la EDT.
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
