package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
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

        nodoAccesoLadoIzq.esVariable();
          
        if(!tipoAcceso.mismoTipo(new TipoInt())){
            throw new ExcepcionSemantica(tokenDecremento, "el tipo de la variable debe ser entero");
        }
    }
}
