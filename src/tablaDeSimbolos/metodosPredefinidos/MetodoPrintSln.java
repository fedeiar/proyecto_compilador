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

        /*
            TODO: no ocupa un byte como los demas, hacer.
        */
        TablaSimbolos.instruccionesMaquina.add("PRNLN");

        TablaSimbolos.instruccionesMaquina.add("STOREFP");
        TablaSimbolos.instruccionesMaquina.add("RET "+ 1); // +1 ya que no tiene this y tiene 1 parametro formal.
    }
}
