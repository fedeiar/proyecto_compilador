package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.ArrayList;
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
            //TODO: cambiar el getConstructor en caso de hacer sobrecarga etapa 4.
            metodo = clase.getMetodoQueConformaParametros(tokenIdMet.getLexema(), getListaTipos());
            if(metodo != null){
                tipoMetodo = metodo.getTipoUnidad();
            }else {
                throw new ExcepcionSemantica(tokenIdMet, "el metodo "+tokenIdMet.getLexema()+" no esta declarado en la clase "+clase.getTokenIdClase().getLexema());
            }
        }else{
            throw new ExcepcionSemantica(tokenIdMet, "el tipo del acceso a izquierda de " +tokenIdMet.getLexema() +" no es una clase valida o no esta declarada"); //que token uso?
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoMetodo);
        } else{
            return tipoMetodo;
        }
    }

    private List<Tipo> getListaTipos() throws ExcepcionSemantica{
        List<Tipo> listaTiposParametrosActuales = new ArrayList<>();
        for(NodoExpresion parametroActual : listaParametrosActuales){
            listaTiposParametrosActuales.add(parametroActual.chequear());
        }
        return listaTiposParametrosActuales;
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

    public void generarCodigo(){ //TODO: esta bien?
        if(metodo.esDinamico()){
            if(!tipoMetodo.mismoTipo(new TipoVoid())){
                TablaSimbolos.instruccionesMaquina.add("RMEM 1 ; Reservo un lugar para el valor de retorno del metodo");
                TablaSimbolos.instruccionesMaquina.add("SWAP ; Pongo this en el tope de la pila");
            }
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
                TablaSimbolos.instruccionesMaquina.add("SWAP ; Pongo this en el tope de la pila");
            }
            TablaSimbolos.instruccionesMaquina.add("DUP ; Duplico this para no perderlo al hacer LOADREF");
            TablaSimbolos.instruccionesMaquina.add("LOADREF 0 ; Cargo la VT");
            TablaSimbolos.instruccionesMaquina.add("LOADREF "+ metodo.getOffset()+" ; Cargo el metodo con su offset en la VT");
            TablaSimbolos.instruccionesMaquina.add("CALL"); 
        } else{ // Es estatico
            if(!tipoMetodo.mismoTipo(new TipoVoid())){
                TablaSimbolos.instruccionesMaquina.add("RMEM 1 ; Reservo un lugar para el valor de retorno del metodo");
            }
            TablaSimbolos.instruccionesMaquina.add("POP ; Descartamos el valor de la variable cargado con LOAD anteriormente ya que no lo necesitamos para hacer la llamada estatica");
            for(NodoExpresion nodoExpresion : listaParametrosActuales){
                nodoExpresion.generarCodigo(); // Computo la expresion del parametro actual i-esimo
            }
            TablaSimbolos.instruccionesMaquina.add("PUSH "+metodo.toStringLabel()+ " ; Pongo la etiqueta del metodo");
            TablaSimbolos.instruccionesMaquina.add("CALL");
        }

        if(nodoEncadenado != null){
            nodoEncadenado.generarCodigo();
        }
    }

    
}
