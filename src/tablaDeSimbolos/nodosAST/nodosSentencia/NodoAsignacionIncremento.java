package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;

public class NodoAsignacionIncremento extends NodoAsignacion{
    
    private Token tokenIncremento;

    public NodoAsignacionIncremento(NodoAcceso nodoAccesoLadoIzq, Token tokenIncremento){
        super(nodoAccesoLadoIzq);
        this.tokenIncremento = tokenIncremento;
    }
}
