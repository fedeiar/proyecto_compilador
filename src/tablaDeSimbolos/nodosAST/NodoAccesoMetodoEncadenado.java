package tablaDeSimbolos.nodosAST;

import java.util.List;

import analizadorLexico.Token;

public class NodoAccesoMetodoEncadenado extends NodoEncadenado{
    
    protected Token tokenIdMet;
    protected List<NodoExpresion> listaParametrosActuales;

    public NodoAccesoMetodoEncadenado(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        this.tokenIdMet = tokenIdMet;
        this.listaParametrosActuales = listaParametrosActuales;
    }
}
