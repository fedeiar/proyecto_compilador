package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
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

    public void chequear() throws ExcepcionSemantica{ 
        Tipo tipoMetodo = TablaSimbolos.unidadActual.getTipoUnidad();
        if(nodoExpresionRetorno == null){
            if( !(tipoMetodo.mismoTipo(new TipoVoid())) ){
                throw new ExcepcionSemantica(tokenReturn, "debe retornarse algo de tipo "+tipoMetodo.getNombreTipo());
            }
        } else{
            if(tipoMetodo.mismoTipo(new TipoVoid())){
                throw new ExcepcionSemantica(tokenReturn, "las unidades con tipo void no pueden retornar valores");
            }
            Tipo tipoExpresion = nodoExpresionRetorno.chequear();
            tipoExpresion.esSubtipo(tipoMetodo);
        }
    }
}
