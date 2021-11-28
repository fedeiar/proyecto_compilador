package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoClase;

public class NodoAccesoSuper extends NodoPrimario{
    
    private Token tokenSuper;

    public NodoAccesoSuper(Token tokenSuper){
        this.tokenSuper = tokenSuper;
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if( !TablaSimbolos.unidadActual.esDinamico() ){
            throw new ExcepcionSemantica(tokenSuper, "no se puede hacer referencia a super en un metodo estatico");
        }

        Tipo tipoClaseAncestro = new TipoClase(TablaSimbolos.claseActual.getTokenIdClaseAncestro());

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoClaseAncestro);
        } else{
            throw new ExcepcionSemantica(tokenSuper, "super no es una expresion valida");
        }
    }

    public boolean esAsignable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esAsignable();
        } else{
            return false;
        }
    }

    public boolean esLlamable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esLlamable();
        } else{
            return false;
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        TablaSimbolos.listaInstruccionesMaquina.add("LOAD 3 ; Apilo this en la pila");
        
        // El nodoEncadenado es no-nulo ya que sino no habría pasado el chequeo de sentencias
        nodoEncadenado.establecerMismoLado(this.esLadoIzquierdoAsignacion);
        nodoEncadenado.generarCodigoSuper(); 
    }
}
