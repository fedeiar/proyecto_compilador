package tablaDeSimbolos.metodosPredefinidos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.Tipo;

public class MetodoRead extends Metodo{ 
    
    public MetodoRead(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super(tokenIdMet, esDinamico, tipoMetodo, claseContenedora);
    }


    public void generarCodigo(){
        TablaSimbolos.listaInstruccionesMaquina.add("LOADFP");
        TablaSimbolos.listaInstruccionesMaquina.add("LOADSP");
        TablaSimbolos.listaInstruccionesMaquina.add("STOREFP");

        TablaSimbolos.listaInstruccionesMaquina.add("READ    ; Lectura de un digito entero");
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH 48 ; Apilamos un 48 para restarselo al ASCII del digito y así obtener el número");
        TablaSimbolos.listaInstruccionesMaquina.add("SUB");
        TablaSimbolos.listaInstruccionesMaquina.add("STORE 3 ; Ponemos el tope de la pila en la locación reservada. En 1 esta ED y en 2 PR, no hay this ni parametros");

        TablaSimbolos.listaInstruccionesMaquina.add("STOREFP");
        TablaSimbolos.listaInstruccionesMaquina.add("RET "+ 0 +" ; +0 ya que no tiene this y no tiene parametros formales");
    }
}
