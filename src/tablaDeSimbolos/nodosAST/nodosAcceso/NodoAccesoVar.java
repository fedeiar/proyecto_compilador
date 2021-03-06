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

    public Tipo chequear() throws ExcepcionSemantica{
        variable = TablaSimbolos.getVarLocalUnidadActual(tokenIdVar.getLexema());
        if(variable == null){
            variable = TablaSimbolos.unidadActual.getParametroFormal(tokenIdVar.getLexema());
            if(variable == null){
                variable = TablaSimbolos.claseActual.getAtributo(tokenIdVar.getLexema());
                if(variable == null){
                    throw new ExcepcionSemantica(tokenIdVar, "la variable "+tokenIdVar.getLexema()+" no fue declarada o no es accesible");
                } else{
                    if(!TablaSimbolos.unidadActual.esDinamico()){
                        throw new ExcepcionSemantica(tokenIdVar, "no se puede acceder a una variable de instancia en una unidad estatica"); 
                    }
                }
            }
        }

        Tipo tipoVariable = variable.getTipo();

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


    // Generacion de codigo intermedio.

    public void generarCodigo(){
        if(variable instanceof Atributo){
            TablaSimbolos.listaInstruccionesMaquina.add("LOAD 3 ; Apilo this en la pila");
            if(!esLadoIzquierdoAsignacion || nodoEncadenado != null){
                TablaSimbolos.listaInstruccionesMaquina.add("LOADREF " + variable.getOffset() +" ; Apilo el valor del atributo en la pila");
            } else{
                TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Pongo el valor de la expresion a asignar en el tope, y la referencia al CIR en tope - 1");
                TablaSimbolos.listaInstruccionesMaquina.add("STOREREF "+ variable.getOffset() +" ; Guardo el valor de la expresi??n en el atributo"); 
            }
        } else if(variable instanceof ParametroFormal || variable instanceof NodoVarLocal){
            if(!esLadoIzquierdoAsignacion || nodoEncadenado != null){
                TablaSimbolos.listaInstruccionesMaquina.add("LOAD "+ variable.getOffset() +" ; Apilo el valor de la variable local o parametro");
            } else{
                TablaSimbolos.listaInstruccionesMaquina.add("STORE "+ variable.getOffset() +" ; Guardo el valor de la expresi??n en la variable");
            }
        }

        if(nodoEncadenado != null){
            nodoEncadenado.establecerMismoLado(this.esLadoIzquierdoAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }
}
