package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Atributo;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.ParametroFormal;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoVarLocal;
import tablaDeSimbolos.tipos.TipoConcreto;
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
        TipoConcreto tipoVariable;
        NodoVarLocal nodoVarLocal = TablaSimbolos.getVarLocalUnidadActual(tokenIdVar.getLexema());
        if(nodoVarLocal != null){
            tipoVariable = nodoVarLocal.getTipo();
        } else{
            ParametroFormal parametroFormal = TablaSimbolos.unidadActual.getParametroFormal(tokenIdVar.getLexema());
            if(parametroFormal != null){
                tipoVariable = parametroFormal.getTipo();
            } else{
                Atributo atributo = TablaSimbolos.claseActual.getAtributo(tokenIdVar.getLexema());
                if(atributo != null){
                    if(TablaSimbolos.unidadActual.esDinamico()){
                        tipoVariable = atributo.getTipo();
                    }
                    else{
                        throw new ExcepcionSemantica(tokenIdVar, "no se puede acceder a una variable de instancia en una unidad estatica");
                    }
                } else{
                    throw new ExcepcionSemantica(tokenIdVar, "la variable "+tokenIdVar.getLexema()+" no fue declarada o no es accesible");
                }
            }
        }

        if(nodoEncadenado != null){
            return nodoEncadenado.chequear(tipoVariable); 
        }
        return tipoVariable;
    }

    /*
    public boolean esVariable(){ //TODO: preg si está bien
        // hace falta chequear todo lo que esta comentado aca abajo?
        
        NodoVarLocal nodoVarLocal = TablaSimbolos.getVarLocalUnidadActual(tokenIdVar.getLexema());
        if(nodoVarLocal == null){
            ParametroFormal parametroFormal = TablaSimbolos.unidadActual.getParametroFormal(tokenIdVar.getLexema());
            if(parametroFormal == null){
                Atributo atributo = TablaSimbolos.claseActual.getAtributo(tokenIdVar.getLexema());
                if(atributo == null){
                    throw new ExcepcionSemantica(tokenIdVar, "la variable "+tokenIdVar.getLexema()+" no fue declarada");
                }else{
                    if(!TablaSimbolos.unidadActual.esDinamico()){
                        throw new ExcepcionSemantica(tokenIdVar, "no se puede acceder a una variable de instancia en una unidad estatica");
                    }
                }
            }
        }
        //hasta aca

        if(nodoEncadenado != null){
            return nodoEncadenado.esVariable(); 
        }
        return true;
    }
*/

    public void esVariable() throws ExcepcionSemantica{
        if(nodoEncadenado != null){
            nodoEncadenado.esVariable();
        } else{
            // No hacer nada, es correcto.
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
