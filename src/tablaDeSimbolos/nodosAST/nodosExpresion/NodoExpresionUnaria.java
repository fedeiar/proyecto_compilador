package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionUnaria extends NodoExpresion{
    
    private Token tokenOperador;
    private NodoOperando nodoOperando;
    
    public NodoExpresionUnaria(Token tokenOperador, NodoOperando nodoOperando){
        this.tokenOperador = tokenOperador;
        this.nodoOperando = nodoOperando;
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if(tokenOperador != null){
            if(tokenOperador.getLexema().equals("+") || tokenOperador.getLexema().equals("-")){
                if(nodoOperando.chequear().mismoTipo(new TipoInt())){
                    return new TipoInt();
                }else{
                    throw new ExcepcionSemantica(tokenOperador, "el operador unario "+ tokenOperador.getLexema() +" esperaba algo de tipo int");
                }
            } else{ // Si no el operador es un !
                if(nodoOperando.chequear().mismoTipo(new TipoBoolean())){
                    return new TipoBoolean();
                } else{
                    throw new ExcepcionSemantica(tokenOperador, "el operador unario "+ tokenOperador.getLexema() +" esperaba algo de tipo boolean");
                }
            }
        } else{
            return nodoOperando.chequear();
        }
    }

    public void esVariable() throws ExcepcionSemantica{ //TODO: esta bien?
        throw new ExcepcionSemantica(tokenOperador, "una expresion unaria no es una variable");
    }

    public void esLlamada() throws ExcepcionSemantica{
        throw new ExcepcionSemantica(tokenOperador, "una expresion unaria no es una llamada");
    }

}
