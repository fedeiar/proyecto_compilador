package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.ArrayList;
import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.*;
public class NodoAccesoMetodo extends NodoAccesoUnidad{
    
    protected Token tokenIdMet;

    public NodoAccesoMetodo(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdMet = tokenIdMet;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien asi?
        //TODO: cambiar el getConstructor en caso de hacer sobrecarga etapa 4.
        Metodo metodo = TablaSimbolos.claseActual.getMetodoQueConformaParametros(tokenIdMet.getLexema(), getListaTipos()); // Si no encuentra nada, es porque no coincidieron o en nombre, o en la lista de parametros.
        if(metodo == null){
            throw new ExcepcionSemantica(tokenIdMet, "el metodo "+tokenIdMet.getLexema()+" no esta declarado o los parametros no conforman");
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

    private List<Tipo> getListaTipos() throws ExcepcionSemantica{
        List<Tipo> listaTiposParametrosActuales = new ArrayList<>();
        for(NodoExpresion parametroActual : listaParametrosActuales){
            listaTiposParametrosActuales.add(parametroActual.chequear());
        }
        return listaTiposParametrosActuales;
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

}
