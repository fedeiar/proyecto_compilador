package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Atributo;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;
public class NodoAccesoVarEncadenada extends NodoEncadenado{
    
    protected Token tokenIdVar;

    private Atributo atributo;

    public NodoAccesoVarEncadenada(Token tokenIdVar){
        this.tokenIdVar = tokenIdVar;
    }

    public Tipo chequear(Tipo tipoIzquierda) throws ExcepcionSemantica{ //TODO: asi esta bien?
        TipoConcreto tipoAtributo;
        Clase clase = TablaSimbolos.getClase(tipoIzquierda.getNombreTipo()); // Con esto ya resolvemos que sea una clase valida?
        if(clase != null){
            atributo = clase.getAtributo(tokenIdVar.getLexema()); 
            if(atributo != null){
                if(atributo.esPublic()){
                    tipoAtributo = atributo.getTipo();
                } else{
                    throw new ExcepcionSemantica(tokenIdVar, "no se puede acceder al atributo "+tokenIdVar.getLexema()+" ya que esta declarado privado");
                }
            }else{
                throw new ExcepcionSemantica(tokenIdVar, "el atributo "+tokenIdVar.getLexema()+" no esta declarado o no es accesible en la clase "+clase.getTokenIdClase().getLexema());
            }
        } else{
            throw new ExcepcionSemantica(tokenIdVar, "el tipo del acceso a izquierda de " +tokenIdVar.getLexema() +" no es una clase valida o no esta declarada");
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoAtributo);
        } else{
            return tipoAtributo;
        }
        
    }

    public Tipo chequearThis(Tipo tipoIzquierda) throws ExcepcionSemantica{
        TipoConcreto tipoAtributo;
        Clase clase = TablaSimbolos.getClase(tipoIzquierda.getNombreTipo()); // Con esto ya resolvemos que sea una clase valida?
        if(clase != null){
            atributo = clase.getAtributo(tokenIdVar.getLexema()); 
            if(atributo != null){ // Como vengo de this, entonces no controlo si es publico o privado ya que debo poder accederlo igual.
                tipoAtributo = atributo.getTipo(); 
            } else{
                throw new ExcepcionSemantica(tokenIdVar, "el atributo "+tokenIdVar.getLexema()+" no esta declarado o no es accesible en la clase "+clase.getTokenIdClase().getLexema());
            }
        } else{
            throw new ExcepcionSemantica(tokenIdVar, "el tipo del acceso a izquierda de " +tokenIdVar.getLexema() +" no es una clase valida o no esta declarada");
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoAtributo);
        } else{
            return tipoAtributo;
        }
    }

    public boolean esAsignable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esAsignable();
        } else{
            return true;
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

    public void generarCodigo(){ //TODO: esta bien asi?
        if(!esLadoIzquierdoAsignacion || nodoEncadenado != null){
            TablaSimbolos.listaInstruccionesMaquina.add("LOADREF "+ atributo.getOffset() +" ; Apilo el valor del atributo "+atributo.getTokenIdVar().getLexema()+" en la pila");
        } else{
            TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Pongo el valor de la expresion a asignar en el tope, y la referencia al CIR del atributo en tope - 1");
            TablaSimbolos.listaInstruccionesMaquina.add("STOREREF "+ atributo.getOffset() +" ; Guardo el valor de la expresión en el atributo "+ atributo.getTokenIdVar().getLexema()); 
        }
        
        if(nodoEncadenado != null){
            nodoEncadenado.establecerMismoLado(this.esLadoIzquierdoAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }
}
