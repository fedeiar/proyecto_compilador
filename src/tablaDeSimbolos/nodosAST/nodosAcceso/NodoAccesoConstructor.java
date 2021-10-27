package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;

public class NodoAccesoConstructor extends NodoAccesoUnidad{
    
    protected Token tokenIdClase;

    public NodoAccesoConstructor(Token tokenIdClase, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdClase = tokenIdClase;
    }
}
