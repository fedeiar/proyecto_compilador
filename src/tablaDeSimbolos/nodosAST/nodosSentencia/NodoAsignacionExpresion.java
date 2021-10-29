package tablaDeSimbolos.nodosAST.nodosSentencia;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;
public class NodoAsignacionExpresion extends NodoAsignacion{
    
    private NodoExpresion nodoExpresionLadoDer;

    public NodoAsignacionExpresion(NodoAcceso nodoAccesoLadoIzq, NodoExpresion nodoExpresionLadoDer){
        super(nodoAccesoLadoIzq);
        this.nodoExpresionLadoDer = nodoExpresionLadoDer;
    }

    public void chequear() throws ExcepcionSemantica{ //TODO: preguntar como resolver lo del acceso y los encadendados.
        TipoMetodo tipoAcceso = nodoAccesoLadoIzq.chequear();
        //TODO: preguntar hacer todo eso del encadenado

        TipoMetodo tipoExpresion = nodoExpresionLadoDer.chequear();
        
    }
    
}
