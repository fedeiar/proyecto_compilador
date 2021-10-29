package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.tipos.*;
public class NodoThis extends NodoPrimario{

    private Token tokenThis;

    public NodoThis(Token tokenThis){
        this.tokenThis = tokenThis;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        return new TipoClase(TablaSimbolos.claseActual.getTokenIdClase());
    }
    
}
