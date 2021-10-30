package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.ArrayList;
import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.Constructor;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.Tipo;

public class NodoAccesoConstructor extends NodoAccesoUnidad{
    
    protected Token tokenIdClase;

    public NodoAccesoConstructor(Token tokenIdClase, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdClase = tokenIdClase;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien asi?
        Clase claseDelConstructor = TablaSimbolos.getClase(tokenIdClase.getLexema());
        //TODO: cambiar el getConstructor en caso de hacer sobrecarga etapa 4.
        Constructor constructor = claseDelConstructor.getConstructorQueConformaParametros(listaParametrosActuales); // Si no encuentra nada, es porque no coincidieron o en nombre, o en la lista de parametros.
        if(constructor == null){
            throw new ExcepcionSemantica(tokenIdClase, "el constructor "+tokenIdClase.getLexema()+" no esta declarado o los parametros no conforman");
        }
        
        Tipo tipoConstructor = constructor.getTipoUnidad();
        
        if(nodoEncadenado == null){
            return tipoConstructor;
        } else{
            return nodoEncadenado.chequear(tipoConstructor);
        }

    }

    public void esVariable() throws ExcepcionSemantica{
        if(nodoEncadenado != null){
            nodoEncadenado.esVariable();
        } else{
            throw new ExcepcionSemantica(tokenIdClase, "el lado izquierdo de una asignacion debe ser una variable");
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
