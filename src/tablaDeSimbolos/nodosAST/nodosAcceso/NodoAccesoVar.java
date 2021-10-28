package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.*;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoVarLocal;
import tablaDeSimbolos.tipos.Tipo;

public class NodoAccesoVar extends NodoPrimario{
    
    private Token tokenIdVar;

    public NodoAccesoVar(Token tokenIdVar){
        this.tokenIdVar = tokenIdVar;
    }

    public Token getToken(){
        return tokenIdVar;
    }

    public Tipo chequear() throws ExcepcionSemantica{  
        Tipo tipoVariable;
        NodoVarLocal nodoVarLocal = TablaSimbolos.getVarLocalUnidadActual(tokenIdVar.getLexema());
        if(nodoVarLocal != null){
            tipoVariable = nodoVarLocal.getTipo();
        } else{
            ParametroFormal parametroFormal = TablaSimbolos.unidadActual.getParametroFormal(tokenIdVar.getLexema());
            if(parametroFormal != null){
                tipoVariable = parametroFormal.getTipo();
            } else{
                //TODO: con la modificacion que se hizo en consolidarAtributos() entonces ya alcanzaria?
                Atributo atributo = TablaSimbolos.claseActual.getAtributo(tokenIdVar.getLexema());
                if(atributo != null){
                    if(TablaSimbolos.unidadActual.esDinamico()){
                        tipoVariable = atributo.getTipo();
                    }
                    else{
                        throw new ExcepcionSemantica(tokenIdVar, "no se puede acceder a una variable de instancia en una unidad estatica");
                    }
                } else{
                    throw new ExcepcionSemantica(tokenIdVar, "la variable "+tokenIdVar.getLexema()+" no fue declarada");
                }
            }
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoVariable);
        }
        return tipoVariable;
    }
}
