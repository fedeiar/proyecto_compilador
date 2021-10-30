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

    public void chequear() throws ExcepcionSemantica{ //TODO: preguntar si esta bien 
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        nodoAccesoLadoIzq.esVariable();
        
        Tipo tipoExpresion = nodoExpresionLadoDer.chequear();

        if(!tipoExpresion.esSubtipo(tipoAcceso)){
            throw new ExcepcionSemantica(tokenAsignacion, "la expresión del lado derecho no conforma con el tipo de la variable del lado izquierdo");
        }
    }
    
}
