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
    private int cantVarLocalesALiberar;

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

        cantVarLocalesALiberar = TablaSimbolos.getBloqueActual().getCantVarLocalesEnUnidad();
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){

        TablaSimbolos.listaInstruccionesMaquina.add("FMEM "+ cantVarLocalesALiberar +" ; Liberamos las variables locales utilizadas en la unidad hasta el momento de hacer return");
        if(unidadContenedora.getTipoUnidad().mismoTipo(new TipoVoid())){
            TablaSimbolos.listaInstruccionesMaquina.add("STOREFP ; Actualizamos el FP para que apunte al RA del llamador");
            TablaSimbolos.listaInstruccionesMaquina.add("RET "+ unidadContenedora.getOffsetRetornoUnidad() +" ; Retornamos de la unidad liberando n lugares en la pila");
        } else{ // Devuelve algo y tiene una expresion.
            nodoExpresionRetorno.generarCodigo();
            TablaSimbolos.listaInstruccionesMaquina.add("STORE "+ unidadContenedora.getOffsetStoreValorRetorno() +" ; Colocamos el valor de la expresion en la locacion reservada para el retorno");
            TablaSimbolos.listaInstruccionesMaquina.add("STOREFP ; Actualizamos el FP para que apunte al RA del llamador");
            TablaSimbolos.listaInstruccionesMaquina.add("RET "+ unidadContenedora.getOffsetRetornoUnidad() +" ; Retornamos de la unidad liberando n lugares en la pila");
        }


    }
    
}
