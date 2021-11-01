package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;
public class NodoThis extends NodoPrimario{

    private Token tokenThis;

    public NodoThis(Token tokenThis){
        this.tokenThis = tokenThis;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        if( ! TablaSimbolos.unidadActual.esDinamico()){
            throw new ExcepcionSemantica(tokenThis, "no se puede hacer referencia a this en un metodo estatico");
        }
        Tipo tipoClaseActual = new TipoClase(TablaSimbolos.claseActual.getTokenIdClase()); // Esto me asegura que ya existe la clase

        if(nodoEncadenado != null){
            return nodoEncadenado.chequearThis(tipoClaseActual); //TODO: esta bien llamar a este en lugar de al chequear() convencional?
        }
        return tipoClaseActual;
    }

    public void esVariable() throws ExcepcionSemantica{
        if(nodoEncadenado != null){
            nodoEncadenado.esVariable();
        } else{
            throw new ExcepcionSemantica(tokenThis, "el lado izquierdo de una asignacion debe ser una variable");
        }
    }

    public void esLlamada() throws ExcepcionSemantica{ //TODO: esta bien?
        if(nodoEncadenado != null){
            nodoEncadenado.esLlamada();
        } else{
            throw new ExcepcionSemantica(tokenThis, "se esperaba una llamada a un metodo o constructor");
        }
    }
    
}
