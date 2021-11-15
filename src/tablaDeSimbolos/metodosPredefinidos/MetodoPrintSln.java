package tablaDeSimbolos.metodosPredefinidos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;

public class MetodoPrintSln extends Metodo{ 
    
    public MetodoPrintSln(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super(tokenIdMet, esDinamico, tipoMetodo, claseContenedora);
    }


    public void generarCodigo(){
        TablaSimbolos.instruccionesMaquina.add("LOADFP");
        TablaSimbolos.instruccionesMaquina.add("LOADSP");
        TablaSimbolos.instruccionesMaquina.add("STOREFP");

        TablaSimbolos.instruccionesMaquina.add("LOAD 3 ; Cargamos el primer parametro que es una referencia al String en la heap"); // Ahi se encuentra el parámetro, en 1 esta ED y en 2 esta PR. (no tiene this ya que es estatico)
        TablaSimbolos.instruccionesMaquina.add("SPRINT"); // Lo consumimos y lo imprimimos
        TablaSimbolos.instruccionesMaquina.add("PRNLN");

        TablaSimbolos.instruccionesMaquina.add("STOREFP");
        TablaSimbolos.instruccionesMaquina.add("RET "+ 1); // +1 ya que no tiene this y tiene 1 parametro formal.
    }
}
