package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;
import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;
public class NodoAccesoMetodo extends NodoAccesoUnidad{
    
    protected Token tokenIdMet;
    protected Metodo metodoLlamado;

    public NodoAccesoMetodo(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdMet = tokenIdMet;
    }

    public Tipo chequear() throws ExcepcionSemantica{
        //TODO: cambiar el getConstructor en caso de hacer sobrecarga etapa 4.
        metodoLlamado = TablaSimbolos.claseActual.getMetodoQueMasConformaParametros(tokenIdMet, NodoAccesoUnidad.getListaTipos(listaParametrosActuales)); // Si no encuentra nada, es porque no coincidieron o en nombre, o en la lista de parametros.
        if(metodoLlamado == null){
            throw new ExcepcionSemantica(tokenIdMet, "el metodo "+tokenIdMet.getLexema()+" no esta declarado o los parametros no conforman");
        }
        if(!TablaSimbolos.unidadActual.esDinamico() && metodoLlamado.esDinamico()){
            throw new ExcepcionSemantica(tokenIdMet, "no se puede hacer referencia al metodo dinamico "+tokenIdMet.getLexema() +" desde un metodo estatico");
        }

        Tipo tipoMetodo = metodoLlamado.getTipoUnidad();
        if(nodoEncadenado == null){
            return tipoMetodo;
        } else{
            return nodoEncadenado.chequear(tipoMetodo);
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
            return true;
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ 
        if(metodoLlamado.esDinamico()){ // Es dinamico
            TablaSimbolos.listaInstruccionesMaquina.add("LOAD 3"); // Cargo this
            if(!metodoLlamado.getTipoUnidad().mismoTipo(new TipoVoid())){
                TablaSimbolos.listaInstruccionesMaquina.add("RMEM 1 ; Reservo lugar para el valor retorno de la llamada");
                TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Pongo this en el tope de la pila");
            }
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
                TablaSimbolos.listaInstruccionesMaquina.add("SWAP"); // Pongo this en el tope de la pila
            }
            TablaSimbolos.listaInstruccionesMaquina.add("DUP ; Duplico this para no perderlo al hacer LOADREF");
            TablaSimbolos.listaInstruccionesMaquina.add("LOADREF 0 ; Cargo la VT");
            TablaSimbolos.listaInstruccionesMaquina.add("LOADREF "+ metodoLlamado.getOffset() +" ; Cargo el metodo con su offset en la VT");
            TablaSimbolos.listaInstruccionesMaquina.add("CALL"); 
            
        } else{ // Es estatico
            if(!metodoLlamado.getTipoUnidad().mismoTipo(new TipoVoid())){
                TablaSimbolos.listaInstruccionesMaquina.add("RMEM 1 ; Reservo un lugar para el valor de retorno del metodo");
            }
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
            }
            TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+ metodoLlamado.toStringLabel() + " ; Pongo la etiqueta del metodo");
            TablaSimbolos.listaInstruccionesMaquina.add("CALL");
        }

        if(nodoEncadenado != null){
            nodoEncadenado.establecerMismoLado(this.esLadoIzquierdoAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }

}
