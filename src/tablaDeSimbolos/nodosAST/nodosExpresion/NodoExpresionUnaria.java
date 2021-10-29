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

    public Tipo chequear() throws ExcepcionSemantica{ //TODO: esta bien hecho?
        if(tokenOperador != null){
            if(tokenOperador.getLexema().equals("+") || tokenOperador.getLexema().equals("-")){
                if(nodoOperando.chequear().mismoTipo(new TipoInt())){
                    return new TipoInt();
                }else{
                    throw new ExcepcionSemantica(tokenOperador, "el operador unario "+ tokenOperador.getLexema() +" esperaba algo de tipo int");
                }
            } else if (tokenOperador.getLexema().equals("!")){
                if(nodoOperando.chequear().mismoTipo(new TipoBoolean())){
                    return new TipoBoolean();
                } else{
                    throw new ExcepcionSemantica(tokenOperador, "el operador unario "+ tokenOperador.getLexema() +" esperaba algo de tipo boolean");
                }
            } else{
                //TODO: que mensaje de error iria? puede ser que nunca suceda un tipo de error asi?
                throw new ExcepcionSemantica(tokenOperador, "el operador unario "+tokenOperador.getLexema()+" no es un operador unario valido");
            }
        } else{
            return nodoOperando.chequear();
        }
    }

}
