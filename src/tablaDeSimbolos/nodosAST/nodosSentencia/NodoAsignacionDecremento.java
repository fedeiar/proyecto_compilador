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

    public void chequear() throws ExcepcionSemantica{ 
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        
        if( !nodoAccesoLadoIzq.esAsignable() ){
            throw new ExcepcionSemantica(tokenDecremento, "el lado izquierdo de un decremento debe terminar en una variable");
        }
        
          
        if(!tipoAcceso.mismoTipo(new TipoInt())){
            throw new ExcepcionSemantica(tokenDecremento, "el tipo de la variable debe ser entero");
        }
    }
}
