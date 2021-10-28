package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;

public class NodoAsignacionDecremento extends NodoAsignacion{
    
    private Token tokenDecremento;

    public NodoAsignacionDecremento(NodoAcceso nodoAccesoLadoIzq, Token tokenDecremento){
        super(nodoAccesoLadoIzq);
        this.tokenDecremento = tokenDecremento;
    }
}
