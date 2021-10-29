package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.Metodo;
public class NodoAccesoMetodo extends NodoAccesoUnidad{
    
    protected Token tokenIdMet;

    public NodoAccesoMetodo(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdMet = tokenIdMet;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien asi?
        String nombreMetodo = NodoAccesoUnidad.toStringNombreUnidad(tokenIdMet, listaParametrosActuales);
        Metodo metodo = TablaSimbolos.claseActual.getMetodo(nombreMetodo); // Si no encuentra nada, es porque no coincidieron o en nombre, o en la lista de parametros.
        if(metodo == null){
            throw new ExcepcionSemantica(tokenIdMet, "el metodo "+tokenIdMet.getLexema()+" no esta declarado");
        }
        if(!TablaSimbolos.unidadActual.esDinamico() && metodo.esDinamico()){
            throw new ExcepcionSemantica(tokenIdMet, "no se puede hacer referencia al metodo dinamico "+tokenIdMet.getLexema() +" desde un metodo estatico");
        }

        Tipo tipoMetodo = metodo.getTipoUnidad();
        if(nodoEncadenado == null){
            return tipoMetodo;
        } else{
            return nodoEncadenado.chequear(tipoMetodo);
        }

    }

}
