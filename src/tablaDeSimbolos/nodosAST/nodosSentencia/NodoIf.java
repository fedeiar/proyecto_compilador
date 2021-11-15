package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoBoolean;

public class NodoIf extends NodoSentencia{
    
    private Token tokenIf;
    private NodoExpresion NodoExpresionCondicion;
    private NodoSentencia nodoSentenciaIf;
    private NodoSentencia nodoSentenciaElse;

    private NodoBloque nodoBloqueIfFicticio;
    private NodoBloque nodoBloqueElseFicticio;

    private static int numeroEtiquetaFinIf = 0;
    private static int numeroEtiquetaElse = 0;

    public NodoIf(Token tokenIf, NodoExpresion condicion, NodoSentencia sentenciaIf){
        this.tokenIf = tokenIf;
        this.NodoExpresionCondicion = condicion;
        this.nodoSentenciaIf = sentenciaIf;
    }


    public void insertarSentenciaElse(NodoSentencia nodoSentenciaElse){
        this.nodoSentenciaElse = nodoSentenciaElse;
    }

    public void chequear() throws ExcepcionSemantica{

        if(NodoExpresionCondicion.chequear().mismoTipo(new TipoBoolean())){ 
            nodoBloqueIfFicticio = new NodoBloque(); // Al tener este bloque ficticio, en caso de que el nodoSentenciaIf (o else) sea una declaración de varLocal, se insertará aca y luego quitada.
            nodoBloqueIfFicticio.chequear(); // De esta manera, tendra el offset adecuado para las varLocales.
            TablaSimbolos.apilarBloqueActual(nodoBloqueIfFicticio);
            
            nodoSentenciaIf.chequear();
            
            TablaSimbolos.desapilarBloqueActual(); // Aca se quitan las variables locales declaradas en caso de que la sentencia no sea un bloque.

            if(nodoSentenciaElse != null){
                nodoBloqueElseFicticio = new NodoBloque(); // Hacemos un bloque ficticio aparte para el else
                nodoBloqueElseFicticio.chequear(); // De esta manera, tendra el offset adecuado para las varLocales.
                TablaSimbolos.apilarBloqueActual(nodoBloqueElseFicticio);

                nodoSentenciaElse.chequear();

                TablaSimbolos.desapilarBloqueActual(); // Aca se quitan las variables locales del else.
            }
        } else{
            throw new ExcepcionSemantica(tokenIf, "se esperaba una expresion de tipo boolean");
        }

    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: esta bien?
        String etiqueta_finIf = nuevaEtiquetaFinIf();
        String etiqueta_else = nuevaEtiquetaElse();
        NodoExpresionCondicion.generarCodigo(); // Apila 1 si da true, 0 si da false.
        if(nodoSentenciaElse == null){
            TablaSimbolos.instruccionesMaquina.add("BF "+ etiqueta_finIf);

            nodoSentenciaIf.generarCodigo();
            nodoBloqueIfFicticio.generarCodigo(); // Por si debemos liberar variables locales
            
            TablaSimbolos.instruccionesMaquina.add(etiqueta_finIf + ": NOP");
        } else{
            TablaSimbolos.instruccionesMaquina.add("BF "+ etiqueta_else);
            nodoSentenciaIf.generarCodigo();
            TablaSimbolos.instruccionesMaquina.add("JUMP "+ etiqueta_finIf);
            TablaSimbolos.instruccionesMaquina.add(etiqueta_else + ":");
            
            nodoSentenciaElse.generarCodigo();
            nodoBloqueElseFicticio.generarCodigo(); // Por si debemos liberar variables locales.

            TablaSimbolos.instruccionesMaquina.add("JUMP "+ etiqueta_finIf); // TODO: no es redundante?
            TablaSimbolos.instruccionesMaquina.add(etiqueta_finIf + ": NOP");
        }
    }

    private String nuevaEtiquetaFinIf(){
        String nuevaEtiqueta = "l_finIf" + numeroEtiquetaFinIf;
        numeroEtiquetaFinIf++;
        return nuevaEtiqueta;
    }

    private String nuevaEtiquetaElse(){
        String nuevaEtiqueta = "l_else" + numeroEtiquetaElse;
        numeroEtiquetaElse++;
        return nuevaEtiqueta;
    }
}
