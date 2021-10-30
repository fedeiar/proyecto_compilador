package tablaDeSimbolos.nodosAST.nodosSentencia;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import analizadorLexico.Token;

public class NodoLlamada extends NodoSentencia{
    
    private Token tokenPuntoYComa;
    private NodoAcceso nodoAcceso;

    public NodoLlamada(Token tokenPuntoYComa, NodoAcceso nodoAcceso){
        this.nodoAcceso = nodoAcceso;
        this.tokenPuntoYComa = tokenPuntoYComa;
    }

    public void chequear() throws ExcepcionSemantica{
        nodoAcceso.chequear();
        try{
            nodoAcceso.esLlamada();  
        } catch(ExcepcionSemantica e){
            throw new ExcepcionSemantica(tokenPuntoYComa, "se esperaba una llamada a metodo o constructor");
        }
    }
}
