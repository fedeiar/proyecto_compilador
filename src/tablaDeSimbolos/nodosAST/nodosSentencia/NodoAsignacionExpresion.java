package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;
public class NodoAsignacionExpresion extends NodoAsignacion{
    
    private Token tokenAsignacion;
    private NodoExpresion nodoExpresionLadoDer;

    public NodoAsignacionExpresion(Token tokenAsignacion, NodoAcceso nodoAccesoLadoIzq, NodoExpresion nodoExpresionLadoDer){
        super(nodoAccesoLadoIzq);
        this.tokenAsignacion = tokenAsignacion;
        this.nodoExpresionLadoDer = nodoExpresionLadoDer;
    }

    public void chequear() throws ExcepcionSemantica{ 
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        if( !nodoAccesoLadoIzq.esAsignable() ){
            throw new ExcepcionSemantica(tokenAsignacion, "el lado izquierdo de una asignacion debe terminar en una variable");
        }

        Tipo tipoExpresion = nodoExpresionLadoDer.chequear();

        if(!tipoExpresion.esSubtipo(tipoAcceso)){
            throw new ExcepcionSemantica(tokenAsignacion, "la expresión del lado derecho no conforma con el tipo de la variable del lado izquierdo");
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoExpresionLadoDer.generarCodigo();
        nodoAccesoLadoIzq.establecerComoLadoIzquierdo();
        nodoAccesoLadoIzq.generarCodigo();
    }

    
    
}
