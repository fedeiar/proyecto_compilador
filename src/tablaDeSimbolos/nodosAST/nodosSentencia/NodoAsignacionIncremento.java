package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.tipos.*;

public class NodoAsignacionIncremento extends NodoAsignacion{
    
    private Token tokenIncremento;

    public NodoAsignacionIncremento(NodoAcceso nodoAccesoLadoIzq, Token tokenIncremento){
        super(nodoAccesoLadoIzq);
        this.tokenIncremento = tokenIncremento;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        try{
            nodoAccesoLadoIzq.esVariable();
        } catch(ExcepcionSemantica e){
            throw new ExcepcionSemantica(tokenIncremento, "el lado izquierdo de un incremento debe ser una variable");
        }
           
        if(!tipoAcceso.mismoTipo(new TipoInt())){
            throw new ExcepcionSemantica(tokenIncremento, "el tipo de la variable debe ser entero");
        }
    }
}
