package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Atributo;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.IVariable;
import tablaDeSimbolos.entidades.ParametroFormal;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosSentencia.NodoVarLocal;
import tablaDeSimbolos.tipos.Tipo;

public class NodoAccesoVar extends NodoPrimario{
    
    private Token tokenIdVar;

    private IVariable variable;

    public NodoAccesoVar(Token tokenIdVar){
        this.tokenIdVar = tokenIdVar;
    }

    public Token getToken(){
        return tokenIdVar;
    }

    public Tipo chequear() throws ExcepcionSemantica{  //TODO: ahora que tenemos IVariable, achicar el codigo de esto.
        Tipo tipoVariable;
        variable = TablaSimbolos.getVarLocalUnidadActual(tokenIdVar.getLexema());
        if(variable != null){
            tipoVariable = variable.getTipo();
        } else{
            variable = TablaSimbolos.unidadActual.getParametroFormal(tokenIdVar.getLexema());
            if(variable != null){
                tipoVariable = variable.getTipo();
            } else{
                variable = TablaSimbolos.claseActual.getAtributo(tokenIdVar.getLexema());
                if(variable != null){
                    if(TablaSimbolos.unidadActual.esDinamico()){
                        tipoVariable = variable.getTipo();
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

    public boolean esAsignable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esAsignable();
        } else{
            return true;
        }
    }

    public boolean esLlamable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esLlamable();
        } else{
            return false;
        }
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){
        if(variable instanceof Atributo){
            TablaSimbolos.instruccionesMaquina.add("LOAD 3 ; Apilo this en la pila");
            if(!esLadoIzquierdoAsignacion || nodoEncadenado != null){
                TablaSimbolos.instruccionesMaquina.add("LOADREF " +variable.getOffset()+" ; Apilo el valor del atributo en la pila");
            } else{
                TablaSimbolos.instruccionesMaquina.add("SWAP ; Pongo el valor de la expresion a asignar en el tope, y al atributo en tope - 1");
                TablaSimbolos.instruccionesMaquina.add("STOREREF "+ variable.getOffset()+" ; Guardo el valor de la expresión en el atributo"); 
            }
        } else if(variable instanceof ParametroFormal || variable instanceof NodoVarLocal){
            if(!esLadoIzquierdoAsignacion || nodoEncadenado != null){
                TablaSimbolos.instruccionesMaquina.add("LOAD "+variable.getOffset()+" ; Apilo el valor de la variable local o parametro");
            } else{
                TablaSimbolos.instruccionesMaquina.add("STORE "+variable.getOffset()+" ; Guardo el valor de la expresión en la variable");
            }
        }

        if(nodoEncadenado != null){
            nodoEncadenado.generarCodigo();
        }
    }
}
