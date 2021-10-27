package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;

public class NodoAccesoMetodo extends NodoAccesoUnidad{
    
    protected Token tokenIdMet;

    public NodoAccesoMetodo(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdMet = tokenIdMet;
    }
}
