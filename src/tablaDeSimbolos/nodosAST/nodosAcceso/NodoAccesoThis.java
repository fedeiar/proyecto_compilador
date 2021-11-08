package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;
public class NodoAccesoThis extends NodoPrimario{

    private Token tokenThis;

    public NodoAccesoThis(Token tokenThis){
        this.tokenThis = tokenThis;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien?
        if( ! TablaSimbolos.unidadActual.esDinamico()){
            throw new ExcepcionSemantica(tokenThis, "no se puede hacer referencia a this en un metodo estatico");
        }
        Tipo tipoClaseActual = new TipoClase(TablaSimbolos.claseActual.getTokenIdClase()); // Esto me asegura que ya existe la clase

        if(nodoEncadenado != null){
            return nodoEncadenado.chequearThis(tipoClaseActual);
        }
        return tipoClaseActual;
    }

    public boolean esAsignable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esAsignable();
        } else{
            return false;
        }
    }

    public boolean esLlamable(){ //TODO: esta bien?
        if(nodoEncadenado != null){
            return nodoEncadenado.esLlamable();
        } else{
            return false;
        }
    }
    

    // Generacion de codigo intermedio

    public void generarCodigo(){
        // TODO
    }
}
