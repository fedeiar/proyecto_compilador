package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionUnaria extends NodoExpresion{
    
    private Token tokenOperador;
    private NodoOperando nodoOperando;
    
    public NodoExpresionUnaria(Token tokenOperador, NodoOperando nodoOperando){
        this.tokenOperador = tokenOperador;
        this.nodoOperando = nodoOperando;
    }

    public Tipo chequear() throws ExcepcionSemantica{ // El tokenOperador nunca va a ser nulo ya que si se crea una expresión unaria es porque hay operador unario. Si no se crea solo el literal.
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
       
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoOperando.generarCodigo();
        if(tokenOperador.getLexema().equals("+")){
            // No hacer nada
        } else if(tokenOperador.getLexema().equals("-")){
            TablaSimbolos.instruccionesMaquina.add("NEG");
        } else{ // Si no el operador es un !
            TablaSimbolos.instruccionesMaquina.add("NOT");
        }
    }

}
