package tablaDeSimbolos.metodosPredefinidos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;

public class MetodoDebugPrint extends Metodo{ 
    
    public MetodoDebugPrint(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super(tokenIdMet, esDinamico, tipoMetodo, claseContenedora);
    }


    public void generarCodigo(){
        TablaSimbolos.listaInstruccionesMaquina.add("LOADFP");
        TablaSimbolos.listaInstruccionesMaquina.add("LOADSP");
        TablaSimbolos.listaInstruccionesMaquina.add("STOREFP");

        TablaSimbolos.listaInstruccionesMaquina.add("LOAD 3 ; Cargamos el primer parametro"); // Ahi se encuentra el parámetro, en 1 esta ED y en 2 esta PR. (no tiene this ya que es estatico)
        TablaSimbolos.listaInstruccionesMaquina.add("IPRINT");
        TablaSimbolos.listaInstruccionesMaquina.add("PRNLN");

        TablaSimbolos.listaInstruccionesMaquina.add("STOREFP");
        TablaSimbolos.listaInstruccionesMaquina.add("RET "+1+" ; Retornamos del metodo liberando el parametro"); // +1 ya que no tiene this y tiene 1 parametro formal.
    }
}
