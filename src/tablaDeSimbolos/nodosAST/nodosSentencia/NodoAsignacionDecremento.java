package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.tipos.*;

public class NodoAsignacionDecremento extends NodoAsignacion{
    
    private Token tokenDecremento;

    public NodoAsignacionDecremento(NodoAcceso nodoAccesoLadoIzq, Token tokenDecremento){
        super(nodoAccesoLadoIzq);
        this.tokenDecremento = tokenDecremento;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        try{
            nodoAccesoLadoIzq.esVariable();
        } catch(ExcepcionSemantica e){
            throw new ExcepcionSemantica(tokenDecremento, "el lado izquierdo de un decremento debe ser una variable");
        }
          
        if(!tipoAcceso.mismoTipo(new TipoInt())){
            throw new ExcepcionSemantica(tokenDecremento, "el tipo de la variable debe ser entero");
        }
    }
}
