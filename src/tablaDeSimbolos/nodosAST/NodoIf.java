package tablaDeSimbolos.nodosAST;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.TipoBoolean;

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

    public void chequear() throws ExcepcionSemantica{
        if(NodoExpresionCondicion.chequear().visitarMismoTipo(new TipoBoolean())){ //TODO: esta bien asi el chequeo?
            nodoSentenciaIf.chequear();
            if(nodoSentenciaElse != null){
                nodoSentenciaElse.chequear();
            }
        } else{
            throw new ExcepcionSemantica(NodoExpresionCondicion.getToken(), "se esperaba una expresion de tipo boolean");
        }
    }
}
