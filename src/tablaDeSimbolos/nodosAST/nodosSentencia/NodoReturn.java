package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;

public class NodoReturn extends NodoSentencia{
    
    private Token tokenReturn;
    private NodoExpresion nodoExpresionRetorno;
    

    public NodoReturn(Token tokenReturn){
        this.tokenReturn = tokenReturn;
    }

    public void insertarExpresion(NodoExpresion nodoExpresionRetorno){
        this.nodoExpresionRetorno = nodoExpresionRetorno;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        Tipo tipoMetodo = TablaSimbolos.unidadActual.getTipoUnidad();
        if(nodoExpresionRetorno == null){
            if(tipoMetodo.mismoTipo(new TipoVoid())){
                throw new ExcepcionSemantica(tokenReturn, "las unidades con tipo void no pueden retornar valores");
            }
        } else{
            Tipo tipoExpresion = nodoExpresionRetorno.chequear();
            tipoExpresion.esSubtipo(tipoMetodo);
        }
    }
}
