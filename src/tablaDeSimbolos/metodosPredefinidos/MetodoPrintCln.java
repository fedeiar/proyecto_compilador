package tablaDeSimbolos.metodosPredefinidos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;

public class MetodoPrintCln extends Metodo{ 
    
    public MetodoPrintCln(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super(tokenIdMet, esDinamico, tipoMetodo, claseContenedora);
    }


    public void generarCodigo(){
        TablaSimbolos.instruccionesMaquina.add("LOADFP");
        TablaSimbolos.instruccionesMaquina.add("LOADSP");
        TablaSimbolos.instruccionesMaquina.add("STOREFP");

        TablaSimbolos.instruccionesMaquina.add("LOAD 3 ; Cargamos el primer parametro"); // Ahi se encuentra el parámetro, en 1 esta ED y en 2 esta PR. (no tiene this ya que es estatico)
        TablaSimbolos.instruccionesMaquina.add("CPRINT"); // Lo consumimos y lo imprimimos
        TablaSimbolos.instruccionesMaquina.add("PRNLN");

        TablaSimbolos.instruccionesMaquina.add("STOREFP");
        TablaSimbolos.instruccionesMaquina.add("RET "+1+" ; Retornamos del metodo liberando el parametro"); // +1 ya que no tiene this y tiene 1 parametro formal.
    }
}
