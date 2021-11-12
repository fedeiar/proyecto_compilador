package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.entidades.Unidad;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;

public class NodoReturn extends NodoSentencia{
    
    private Token tokenReturn;
    private NodoExpresion nodoExpresionRetorno;
    
    private Unidad unidadContenedora;

    public NodoReturn(Token tokenReturn){
        this.tokenReturn = tokenReturn;
    }

    public void insertarExpresion(NodoExpresion nodoExpresionRetorno){
        this.nodoExpresionRetorno = nodoExpresionRetorno;
    }

    public void chequear() throws ExcepcionSemantica{ 
        unidadContenedora = TablaSimbolos.unidadActual;
        Tipo tipoMetodo = unidadContenedora.getTipoUnidad();
        if(nodoExpresionRetorno == null){
            if( !(tipoMetodo.mismoTipo(new TipoVoid())) ){
                throw new ExcepcionSemantica(tokenReturn, "debe retornarse algo de tipo "+tipoMetodo.getNombreTipo());
            }
        } else{
            if(tipoMetodo.mismoTipo(new TipoVoid())){
                throw new ExcepcionSemantica(tokenReturn, "las unidades con tipo void no pueden retornar valores");
            }
            Tipo tipoExpresion = nodoExpresionRetorno.chequear();
            if(!tipoExpresion.esSubtipo(tipoMetodo)){
                throw new ExcepcionSemantica(tokenReturn, "el tipo de la expresion de retorno no conforma con el tipo de retorno del metodo que es "+tipoMetodo.getNombreTipo());
            }
        }
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: esta bien?

        TablaSimbolos.instruccionesMaquina.add("FMEM "+unidadContenedora.getCantVarLocalesALiberar()+" ; Liberamos las variables locales utilizadas");
        if(unidadContenedora.getTipoUnidad().mismoTipo(new TipoVoid())){
            TablaSimbolos.instruccionesMaquina.add("STOREFP ; Actualizamos el FP para que apunte al RA del llamador");
            TablaSimbolos.instruccionesMaquina.add("RET "+unidadContenedora.getOffsetRetornoUnidad()+" ; Retornamos de la unidad liberando n lugares en la pila");
        } else{ // Devuelve algo y tiene una expresion.
            nodoExpresionRetorno.generarCodigo();
            TablaSimbolos.instruccionesMaquina.add("STORE "+unidadContenedora.getOffsetStoreValorRetorno()+ " ; Colocamos el valor de la expresion en la locacion reservada para el retorno");
            TablaSimbolos.instruccionesMaquina.add("STOREFP ; Actualizamos el FP para que apunte al RA del llamador");
            TablaSimbolos.instruccionesMaquina.add("RET "+unidadContenedora.getOffsetRetornoUnidad()+ " ; Retornamos de la unidad liberando n lugares en la pila");
        }


    }
    
}
