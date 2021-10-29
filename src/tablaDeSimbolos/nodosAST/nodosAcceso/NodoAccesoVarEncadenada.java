package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.Clase;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;
import tablaDeSimbolos.tipos.*;
import tablaDeSimbolos.Atributo;
public class NodoAccesoVarEncadenada extends NodoEncadenado{
    
    protected Token tokenIdVar;

    public NodoAccesoVarEncadenada(Token tokenIdVar){
        this.tokenIdVar = tokenIdVar;
    }

    public Tipo chequear(Tipo tipoIzquierda) throws ExcepcionSemantica{ //TODO: asi esta bien?
        TipoConcreto tipoAtributo;
        Clase clase = TablaSimbolos.getClase(tipoIzquierda.getNombreTipo()); // Con esto ya resolvemos que sea una clase valida?
        if(clase != null){
            Atributo atributo = clase.getAtributo(tokenIdVar.getLexema()); //TODO: al manejar los prviados con un $ entonces asi ya alcanza?
            if(atributo != null){
                tipoAtributo = atributo.getTipo();
            }else{
                throw new ExcepcionSemantica(tokenIdVar, "el atributo "+tokenIdVar.getLexema()+" no esta declarado en la clase "+clase.getTokenIdClase().getLexema()+" o bien no es visible");
            }
        } else{
            //throw new ExcepcionSemantica(, tipoIzquierda.getNombreTipo()+" no es una clase valida o no esta declarada"); //que token uso?
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoAtributo);
        } else{
            return tipoAtributo;
        }
        
    }
}
