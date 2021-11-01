package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Atributo;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;
public class NodoAccesoVarEncadenada extends NodoEncadenado{
    
    protected Token tokenIdVar;

    public NodoAccesoVarEncadenada(Token tokenIdVar){
        this.tokenIdVar = tokenIdVar;
    }

    public Tipo chequear(Tipo tipoIzquierda) throws ExcepcionSemantica{ //TODO: asi esta bien?
        TipoConcreto tipoAtributo;
        Clase clase = TablaSimbolos.getClase(tipoIzquierda.getNombreTipo()); // Con esto ya resolvemos que sea una clase valida?
        if(clase != null){
            Atributo atributo = clase.getAtributo(tokenIdVar.getLexema()); 
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

    public void esVariable() throws ExcepcionSemantica{
        if(nodoEncadenado != null){
            nodoEncadenado.esVariable();
        } else{
            // No hacer nada, es correcto
        }
    }

    public void esLlamada() throws ExcepcionSemantica{ //TODO: esta bien?
        if(nodoEncadenado != null){
            nodoEncadenado.esLlamada();
        } else{
            throw new ExcepcionSemantica(tokenIdVar, "se esperaba una llamada a un metodo o constructor");
        }
    }
}
