package tablaDeSimbolos.nodosAST;

import java.util.List;

import analizadorLexico.Token;

public class NodoAccesoMetodo extends NodoAccesoUnidad{
    
    protected Token tokenIdMet;

    public NodoAccesoMetodo(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdMet = tokenIdMet;
    }
}
