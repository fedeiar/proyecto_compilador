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

            nodoSentenciaIf.chequear();
            if(nodoSentenciaIf instanceof NodoVarLocal){
                throw new ExcepcionSemantica(tokenIf, "no puede declararse una variable local como sentencia de un if");
            }

            if(nodoSentenciaElse != null){

                nodoSentenciaElse.chequear();
                if(nodoSentenciaElse instanceof NodoVarLocal){
                    throw new ExcepcionSemantica(tokenIf, "no puede declararse una variable local como sentencia de un else");
                }

            }
        } else{
            throw new ExcepcionSemantica(tokenIf, "se esperaba una expresion de tipo boolean");
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        String etiqueta_finIf = nuevaEtiquetaFinIf();
        String etiqueta_else = nuevaEtiquetaElse();

        NodoExpresionCondicion.generarCodigo(); // Apila 1 si da true, 0 si da false.
        if(nodoSentenciaElse == null){
            TablaSimbolos.instruccionesMaquina.add("BF "+ etiqueta_finIf);

            nodoSentenciaIf.generarCodigo();
            
            TablaSimbolos.instruccionesMaquina.add(etiqueta_finIf + ": NOP");
        } else{
            TablaSimbolos.instruccionesMaquina.add("BF "+ etiqueta_else);
            nodoSentenciaIf.generarCodigo();
            TablaSimbolos.instruccionesMaquina.add("JUMP "+ etiqueta_finIf);
            TablaSimbolos.instruccionesMaquina.add(etiqueta_else + ":");
            
            nodoSentenciaElse.generarCodigo();

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
