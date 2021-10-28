package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoBoolean;

public class NodoIf extends NodoSentencia{
    
    private Token tokenIf;
    private NodoExpresion NodoExpresionCondicion;
    private NodoSentencia nodoSentenciaIf;
    private NodoSentencia nodoSentenciaElse;

    public NodoIf(Token tokenIf, NodoExpresion condicion, NodoSentencia sentenciaIf){
        this.tokenIf = tokenIf;
        this.NodoExpresionCondicion = condicion;
        this.nodoSentenciaIf = sentenciaIf;
    }


    public void insertarSentenciaElse(NodoSentencia nodoSentenciaElse){
        nodoSentenciaElse = this.nodoSentenciaElse;
    }

    public void chequear() throws ExcepcionSemantica{
        if(NodoExpresionCondicion.chequear().visitarMismoTipo(new TipoBoolean())){ 
            nodoSentenciaIf.chequear();
            if(nodoSentenciaElse != null){
                nodoSentenciaElse.chequear();
            }
        } else{
            throw new ExcepcionSemantica(tokenIf, "se esperaba una expresion de tipo boolean");
        }
    }
}
