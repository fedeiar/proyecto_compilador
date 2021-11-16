package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.tipos.*;

public class NodoAsignacionIncremento extends NodoAsignacion{
    
    private Token tokenIncremento;

    public NodoAsignacionIncremento(NodoAcceso nodoAccesoLadoIzq, Token tokenIncremento){
        super(nodoAccesoLadoIzq);
        this.tokenIncremento = tokenIncremento;
    }

    public void chequear() throws ExcepcionSemantica{ 
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        if( !nodoAccesoLadoIzq.esAsignable() ){
            throw new ExcepcionSemantica(tokenIncremento, "el lado izquierdo de un incremento debe terminar en una variable");
        }
           
        if(!tipoAcceso.mismoTipo(new TipoInt())){
            throw new ExcepcionSemantica(tokenIncremento, "el tipo de la variable debe ser entero");
        }
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoAccesoLadoIzq.generarCodigo();
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH 1 ; agregamos un 1 para incrementar la variable en la pila");
        TablaSimbolos.listaInstruccionesMaquina.add("ADD ; obtenemos la expresion resultante del incremento");
        nodoAccesoLadoIzq.establecerComoLadoIzquierdo();
        nodoAccesoLadoIzq.generarCodigo();
    }
    
}
