package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;

public class NodoAccesoMetodoEncadenado extends NodoEncadenado{
    
    protected Token tokenIdMet;
    protected List<NodoExpresion> listaParametrosActuales;

    private Tipo tipoMetodo;
    private Metodo metodo;

    public NodoAccesoMetodoEncadenado(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        this.tokenIdMet = tokenIdMet;
        this.listaParametrosActuales = listaParametrosActuales;
    }

    public Tipo chequear(Tipo tipoIzquierda) throws ExcepcionSemantica{
        Clase clase = TablaSimbolos.getClase(tipoIzquierda.getNombreTipo()); // Con esto ya resolvemos que sea una clase valida
        if(clase != null){
            metodo = clase.getMetodoQueMasConformaParametros(tokenIdMet, NodoAccesoUnidad.getListaTipos(listaParametrosActuales));
            if(metodo != null){
                tipoMetodo = metodo.getTipoUnidad();
            }else {
                throw new ExcepcionSemantica(tokenIdMet, "el metodo "+tokenIdMet.getLexema()+" no esta declarado en la clase "+clase.getTokenIdClase().getLexema());
            }
        }else{
            throw new ExcepcionSemantica(tokenIdMet, "el tipo del acceso a izquierda de " +tokenIdMet.getLexema() +" no es una clase valida o no esta declarada");
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoMetodo);
        } else{
            return tipoMetodo;
        }
    }

    public Tipo chequearThis(Tipo tipoIzquierda) throws ExcepcionSemantica{
        return chequear(tipoIzquierda);
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
            return true;
        }
    }
    
    // Generacion de codigo intermedio

    public void generarCodigo(){
        if(metodo.esDinamico()){
            if(!tipoMetodo.mismoTipo(new TipoVoid())){
                TablaSimbolos.listaInstruccionesMaquina.add("RMEM 1 ; Reservo un lugar para el valor de retorno del metodo");
                TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Pongo this en el tope de la pila");
            }
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
                TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Pongo this en el tope de la pila");
            }
            TablaSimbolos.listaInstruccionesMaquina.add("DUP ; Duplico this para no perderlo al hacer LOADREF");
            TablaSimbolos.listaInstruccionesMaquina.add("LOADREF 0 ; Cargo la VT");
            TablaSimbolos.listaInstruccionesMaquina.add("LOADREF "+ metodo.getOffset() +" ; Cargo el metodo con su offset en la VT");
            TablaSimbolos.listaInstruccionesMaquina.add("CALL"); 
        } else{ // Es estatico
            TablaSimbolos.listaInstruccionesMaquina.add("POP ; Descartamos el valor de la referencia cargada anteriormente ya que no lo necesitamos para hacer la llamada estatica");
            if(!tipoMetodo.mismoTipo(new TipoVoid())){
                TablaSimbolos.listaInstruccionesMaquina.add("RMEM 1 ; Reservo un lugar para el valor de retorno del metodo");
            }
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
            }
            TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+metodo.toStringLabel()+ " ; Pongo la etiqueta del metodo");
            TablaSimbolos.listaInstruccionesMaquina.add("CALL");
        }

        if(nodoEncadenado != null){
            nodoEncadenado.establecerMismoLado(this.esLadoIzquierdoAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }

    public void generarCodigoSuper( ){
        // TODO: el metodo ya si o si es el del padre, porque en el chequear() el tipoIzquierda es el tipo q devuelve super.
        if(metodo.esDinamico()){
            if(!tipoMetodo.mismoTipo(new TipoVoid())){
                TablaSimbolos.listaInstruccionesMaquina.add("RMEM 1 ; Reservo un lugar para el valor de retorno del metodo");
                TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Pongo this en el tope de la pila");
            }
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
                TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Pongo this en el tope de la pila");
            }
            TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+ metodo.toStringLabel() +" ; Pongo la etiqueta del metodo definido en el padre");
            TablaSimbolos.listaInstruccionesMaquina.add("CALL");
        } else{
            TablaSimbolos.listaInstruccionesMaquina.add("POP ; Descartamos el valor de la referencia cargada anteriormente ya que no lo necesitamos para hacer la llamada estatica");
            if(!tipoMetodo.mismoTipo(new TipoVoid())){
                TablaSimbolos.listaInstruccionesMaquina.add("RMEM 1 ; Reservo un lugar para el valor de retorno del metodo");
            }
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
            }
            TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+metodo.toStringLabel()+ " ; Pongo la etiqueta del metodo");
            TablaSimbolos.listaInstruccionesMaquina.add("CALL");
        }

        if(nodoEncadenado != null){
            nodoEncadenado.establecerMismoLado(this.esLadoIzquierdoAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }


}
