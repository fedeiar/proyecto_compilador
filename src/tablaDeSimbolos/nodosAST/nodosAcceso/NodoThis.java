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

    public TipoConcreto chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        if( ! TablaSimbolos.unidadActual.esDinamico()){
            throw new ExcepcionSemantica(tokenThis, "no se puede hacer referencia a this en un metodo estatico");
        }
        return new TipoClase(TablaSimbolos.claseActual.getTokenIdClase());
    }
    
}
