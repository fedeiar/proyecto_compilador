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

    public TipoMetodo chequear(TipoMetodo tipoIzquierda) throws ExcepcionSemantica{ //TODO: esta bien?
        TipoMetodo tipoMetodo;
        Clase clase = TablaSimbolos.getClase(tipoIzquierda.getNombreTipo()); // Con esto ya resolvemos que sea una clase valida?
        if(clase != null){
            Metodo metodo = clase.getMetodo(NodoAccesoUnidad.toStringNombreUnidad(tokenIdMet, listaParametrosActuales));
            if(metodo != null){
                if( !TablaSimbolos.unidadActual.esDinamico() && metodo.esDinamico()){
                    throw new ExcepcionSemantica(tokenIdMet, "no se puede hacer referencia al metodo dinamico "+tokenIdMet.getLexema() +" desde un metodo estatico");
                }else{
                    tipoMetodo = metodo.getTipoMetodo();
                }
            }else{
                throw new ExcepcionSemantica(tokenIdMet, "el metodo "+tokenIdMet.getLexema()+" no esta declarado en la clase "+clase.getTokenIdClase().getLexema());
            }
        }else{
            //throw new ExcepcionSemantica(, tipoIzquierda.getNombreTipo()+" no es una clase valida o no esta declarada"); //que token uso?
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoMetodo);
        } else{
            return tipoMetodo;
        }

    }

}
