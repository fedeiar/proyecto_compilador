package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoBoolean;

public class NodoFor extends NodoSentencia{
    
    private Token tokenFor; //TODO: ponerla en la EDT.
    private NodoVarLocal nodoVarLocal;
    private NodoExpresion nodoExpresionBooleana;
    private NodoAsignacion nodoAsignacion;
    private NodoSentencia nodoSentencia;

    public NodoFor(Token tokenFor, NodoVarLocal nodoVarLocal, NodoExpresion nodoExpresion, NodoAsignacion nodoAsignacion, NodoSentencia nodoSentencia){
        this.tokenFor = tokenFor;
        this.nodoVarLocal = nodoVarLocal;
        this.nodoExpresionBooleana = nodoExpresion;
        this.nodoAsignacion = nodoAsignacion;
        this.nodoSentencia = nodoSentencia;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien? esta bien el token en error de la excepcion?
        nodoVarLocal.chequear(); //TODO: no nos interesa el tipo?
        if(! nodoExpresionBooleana.chequear().mismoTipo(new TipoBoolean())){
            throw new ExcepcionSemantica(tokenFor, "la expresión de condicion del for debe ser de tipo boolean");
        }
        nodoAsignacion.chequear();
        nodoSentencia.chequear();
    }

    
}
