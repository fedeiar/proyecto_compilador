package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoBoolean;
import tablaDeSimbolos.entidades.TablaSimbolos;

public class NodoFor extends NodoSentencia{
    
    private Token tokenFor;
    private NodoVarLocal nodoVarLocal;
    private NodoExpresion nodoExpresionBooleana;
    private NodoAsignacion nodoAsignacion;
    private NodoSentencia nodoSentenciaFor;

    private NodoBloque nodoBloqueForFicticio;
    private static int numeroEtiquetaComienzo = 0;
    private static int numeroEtiquetaFin = 0;

    public NodoFor(Token tokenFor, NodoVarLocal nodoVarLocal, NodoExpresion nodoExpresion, NodoAsignacion nodoAsignacion, NodoSentencia nodoSentencia){
        this.tokenFor = tokenFor;
        this.nodoVarLocal = nodoVarLocal;
        this.nodoExpresionBooleana = nodoExpresion;
        this.nodoAsignacion = nodoAsignacion;
        this.nodoSentenciaFor = nodoSentencia;
    }

    public void chequear() throws ExcepcionSemantica{ 
        nodoBloqueForFicticio = new NodoBloque(); // Idem a If, la declaración de la varLocal se insertará aca asi luego no es visible fuera del for.
        nodoBloqueForFicticio.establecerOffsetDisponibleVarLocal(); // De esta manera, tendra el offset adecuado para las varLocales del bloque (si hay).
        TablaSimbolos.apilarBloqueActual(nodoBloqueForFicticio);
        
        nodoVarLocal.chequear();
        if(! nodoExpresionBooleana.chequear().mismoTipo(new TipoBoolean())){
            throw new ExcepcionSemantica(tokenFor, "la expresión de condicion del for debe ser de tipo boolean");
        }
        nodoAsignacion.chequear();
        nodoSentenciaFor.chequear();

        if(nodoSentenciaFor instanceof NodoVarLocal){
            throw new ExcepcionSemantica(tokenFor, "no puede declararse una variable local como sentencia de un for");
        }

        TablaSimbolos.desapilarBloqueActual();
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){
        String etiqueta_ComienzoFor = nuevaEtiquetaComienzoFor();
        String etiqueta_FinFor = nuevaEtiquetaFinFor();

        nodoVarLocal.generarCodigo();
        TablaSimbolos.instruccionesMaquina.add(etiqueta_ComienzoFor + ":");
        nodoExpresionBooleana.generarCodigo();
        TablaSimbolos.instruccionesMaquina.add("BF "+etiqueta_FinFor);
        
        nodoSentenciaFor.generarCodigo();
        nodoAsignacion.generarCodigo();
        TablaSimbolos.instruccionesMaquina.add("JUMP "+ etiqueta_ComienzoFor);
        TablaSimbolos.instruccionesMaquina.add(etiqueta_FinFor + ": NOP");

        nodoBloqueForFicticio.liberarVarLocales(); // Para liberar la memoria de la variable local declarada en el encabezado del for.
    }

    private String nuevaEtiquetaComienzoFor(){
        String nuevaEtiqueta = "l_comienzoFor" + numeroEtiquetaComienzo;
        numeroEtiquetaComienzo++;
        return nuevaEtiqueta;
    }

    private String nuevaEtiquetaFinFor(){
        String nuevaEtiqueta = "l_finFor" + numeroEtiquetaFin;
        numeroEtiquetaFin++;
        return nuevaEtiqueta;
    }


    
}
