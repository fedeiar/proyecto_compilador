package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoAsignacionIncremento extends NodoAsignacion{
    
    private Token tokenIncremento;

    public NodoAsignacionIncremento(NodoAcceso nodoAccesoLadoIzq, Token tokenIncremento){
        super(nodoAccesoLadoIzq);
        this.tokenIncremento = tokenIncremento;
    }
}
