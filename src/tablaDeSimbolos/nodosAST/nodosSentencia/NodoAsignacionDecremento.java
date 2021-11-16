package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.tipos.*;

public class NodoAsignacionDecremento extends NodoAsignacion{
    
    private Token tokenDecremento;

    public NodoAsignacionDecremento(NodoAcceso nodoAccesoLadoIzq, Token tokenDecremento){
        super(nodoAccesoLadoIzq);
        this.tokenDecremento = tokenDecremento;
    }

    public void chequear() throws ExcepcionSemantica{ 
        Tipo tipoAcceso = nodoAccesoLadoIzq.chequear();

        
        if( !nodoAccesoLadoIzq.esAsignable() ){
            throw new ExcepcionSemantica(tokenDecremento, "el lado izquierdo de un decremento debe terminar en una variable");
        }
        
          
        if(!tipoAcceso.mismoTipo(new TipoInt())){
            throw new ExcepcionSemantica(tokenDecremento, "el tipo de la variable debe ser entero");
        }
    }

    public void generarCodigo(){
        nodoAccesoLadoIzq.generarCodigo();
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH 1 ; agregamos un 1 para incrementar la variable en la pila");
        TablaSimbolos.listaInstruccionesMaquina.add("SUB ; obtenemos la expresion resultante del incremento");
        nodoAccesoLadoIzq.establecerComoLadoIzquierdo();
        nodoAccesoLadoIzq.generarCodigo();
    }
}
