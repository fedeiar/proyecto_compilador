package tablaDeSimbolos.metodosPredefinidos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;

public class MetodoRead extends Metodo{ 
    
    public MetodoRead(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super(tokenIdMet, esDinamico, tipoMetodo, claseContenedora);
    }


    public void generarCodigo(){ //TODO: preguntar.
        TablaSimbolos.instruccionesMaquina.add("LOADFP");
        TablaSimbolos.instruccionesMaquina.add("LOADSP");
        TablaSimbolos.instruccionesMaquina.add("STOREFP");

        TablaSimbolos.instruccionesMaquina.add("READ    ; Lectura de un valor entero");
        //TablaSimbolos.instruccionesMaquina.add("PUSH 99");
        TablaSimbolos.instruccionesMaquina.add("STORE 3 ; Ponemos el tope de la pila en la locación reservada. En 1 esta ED y en 2 PR, no hay this ni parametros");

        TablaSimbolos.instruccionesMaquina.add("STOREFP");
        TablaSimbolos.instruccionesMaquina.add("RET "+ 0 +" ; +0 ya que no tiene this y no tiene parametros formales");
    }
}