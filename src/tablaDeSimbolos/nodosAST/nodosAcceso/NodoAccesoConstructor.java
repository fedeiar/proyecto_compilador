package tablaDeSimbolos.nodosAST.nodosAcceso;

import tablaDeSimbolos.Clase;
import tablaDeSimbolos.Constructor;
import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.Tipo;

public class NodoAccesoConstructor extends NodoAccesoUnidad{
    
    protected Token tokenIdClase;

    public NodoAccesoConstructor(Token tokenIdClase, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdClase = tokenIdClase;
    }

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien asi?
        String nombreConstructor = NodoAccesoUnidad.toStringNombreUnidad(tokenIdClase, listaParametrosActuales);
        Clase claseDelConstructor = TablaSimbolos.getClase(tokenIdClase.getLexema());
        Constructor constructor = claseDelConstructor.getConstructor(nombreConstructor); // Si no encuentra nada, es porque no coincidieron o en nombre, o en la lista de parametros.
        if(constructor == null){
            throw new ExcepcionSemantica(tokenIdClase, "el constructor "+tokenIdClase.getLexema()+" no esta declarado");
        }
        
        Tipo tipoConstructor = constructor.getTipoUnidad();
        if(nodoEncadenado == null){ //TODO: esta bien controlar esto de los encadenados también aca?
            return tipoConstructor;
        } else{
            return nodoEncadenado.chequear(tipoConstructor);
        }

    }


}
