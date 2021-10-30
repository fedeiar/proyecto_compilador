package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.*;

public class NodoAsignacionIncremento extends NodoAsignacion{
    
    private Token tokenIncremento;

    public NodoAsignacionIncremento(NodoAcceso nodoAccesoLadoIzq, Token tokenIncremento){
        super(nodoAccesoLadoIzq);
        this.tokenIncremento = tokenIncremento;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        nodoAccesoLadoIzq.esVariable();
           
        if(!tipoAcceso.mismoTipo(new TipoInt())){
            throw new ExcepcionSemantica(tokenIncremento, "el tipo de la variable debe ser entero");
        }
    }
}
