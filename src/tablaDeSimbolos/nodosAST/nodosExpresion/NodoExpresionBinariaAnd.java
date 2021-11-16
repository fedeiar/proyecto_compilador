package tablaDeSimbolos.nodosAST.nodosExpresion;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.*;

public class NodoExpresionBinariaAnd extends NodoExpresionBinaria{
    

    public NodoExpresionBinariaAnd(Token tokenOperadorBinario){
        super(tokenOperadorBinario);
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        if(nodoExpresionLadoIzq.chequear().mismoTipo(new TipoBoolean()) && nodoExpresionLadoDer.chequear().mismoTipo(new TipoBoolean())){
            return new TipoBoolean();
        } else{
            throw new ExcepcionSemantica(tokenOperadorBinario, "el operador binario "+ tokenOperadorBinario.getLexema()+" solo funciona con tipos booleanos");
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoExpresionLadoIzq.generarCodigo();
        nodoExpresionLadoDer.generarCodigo();
        TablaSimbolos.listaInstruccionesMaquina.add("AND");
    }
}
