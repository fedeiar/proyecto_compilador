package tablaDeSimbolos.metodosPredefinidos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;

public class MetodoPrintln extends Metodo{ 
    
    public MetodoPrintln(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super(tokenIdMet, esDinamico, tipoMetodo, claseContenedora);
    }


    public void generarCodigo(){
        TablaSimbolos.instruccionesMaquina.add("LOADFP");
        TablaSimbolos.instruccionesMaquina.add("LOADSP");
        TablaSimbolos.instruccionesMaquina.add("STOREFP");

        TablaSimbolos.instruccionesMaquina.add("PRNLN"); // Lo consumimos y lo imprimimos

        TablaSimbolos.instruccionesMaquina.add("STOREFP");
        TablaSimbolos.instruccionesMaquina.add("RET "+ 0 +" ; +0 ya que no tiene this y no tiene parametros formales");
    }
}
