package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionBinariaNotIgual extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaNotIgual(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        Tipo tipoExpresionLadoIzq = nodoExpresionLadoIzq.chequear();
        Tipo tipoExpresionLadoDer = nodoExpresionLadoDer.chequear();
        if(tipoExpresionLadoIzq.soySubtipo(tipoExpresionLadoDer) || tipoExpresionLadoDer.soySubtipo(tipoExpresionLadoIzq)){
            return new TipoBoolean();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador binario "+ tokenOperadorBinario.getLexema()+" solo funciona con tipos conformantes");
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoExpresionLadoIzq.generarCodigo();
        nodoExpresionLadoDer.generarCodigo();
        TablaSimbolos.listaInstruccionesMaquina.add("NE");
    }


}
