package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.Metodo;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.Clase;
import tablaDeSimbolos.TablaSimbolos;

public class NodoAccesoMetodoEncadenado extends NodoEncadenado{
    
    protected Token tokenIdMet;
    protected List<NodoExpresion> listaParametrosActuales;

    public NodoAccesoMetodoEncadenado(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        this.tokenIdMet = tokenIdMet;
        this.listaParametrosActuales = listaParametrosActuales;
    }

    public Tipo chequear(Tipo tipoIzquierda) throws ExcepcionSemantica{ //TODO: esta bien?
        Tipo tipoMetodo;
        Clase clase = TablaSimbolos.getClase(tipoIzquierda.getNombreTipo()); // Con esto ya resolvemos que sea una clase valida
        if(clase != null){
            //TODO: cambiar el getConstructor en caso de hacer sobrecarga etapa 4.
            Metodo metodo = clase.getMetodoQueConformaParametros(tokenIdMet.getLexema(), listaParametrosActuales);
            if(metodo != null){
                tipoMetodo = metodo.getTipoUnidad();
            }else {
                throw new ExcepcionSemantica(tokenIdMet, "el metodo "+tokenIdMet.getLexema()+" no esta declarado en la clase "+clase.getTokenIdClase().getLexema());
            }
        }else{
            throw new ExcepcionSemantica(tokenIdMet, tipoIzquierda.getNombreTipo()+" no es una clase valida o no esta declarada"); //que token uso?
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoMetodo);
        } else{
            return tipoMetodo;
        }
    }

    public void esVariable() throws ExcepcionSemantica{
        if(nodoEncadenado != null){
            nodoEncadenado.esVariable();
        } else{
            throw new ExcepcionSemantica(tokenIdMet, "el lado izquierdo de una asignacion debe ser una variable");
        }
    }

    public void esLlamada() throws ExcepcionSemantica{ //TODO: esta bien?
        if(nodoEncadenado != null){
            nodoEncadenado.esLlamada();
        } else{
            // No hacer nada, es correcto
        }
    }

}
