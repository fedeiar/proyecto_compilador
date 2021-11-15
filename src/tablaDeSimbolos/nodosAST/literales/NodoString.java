package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.TipoConcreto;
import tablaDeSimbolos.tipos.TipoString;

public class NodoString extends NodoOperando{
    
    private Token tokenLitString;

    public NodoString(Token tokenLitString){
        this.tokenLitString = tokenLitString;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoString();
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: esta bien?
        String string = tokenLitString.getLexema();
        string = string.substring(1, string.length() - 1);

        TablaSimbolos.instruccionesMaquina.add("RMEM 1 ; Reservo memoria para el puntero al comienzo del String que se almacenara en .HEAP");
        TablaSimbolos.instruccionesMaquina.add("PUSH "+ (string.length() + 1) +" ; Parametro del malloc. Reservo n + 1 lugares en el heap, donde n es la cantidad de letras del String y +1 para el caracter terminador (valor 0)");
        TablaSimbolos.instruccionesMaquina.add("PUSH simple_malloc");
        TablaSimbolos.instruccionesMaquina.add("CALL");
        for(int i = 0; i < string.length(); i++){
            TablaSimbolos.instruccionesMaquina.add("DUP ; Duplico la referencia del comienzo del String ya que STOREREF la consumira para almacenar el proximo caracter");
            TablaSimbolos.instruccionesMaquina.add("PUSH "+'\''+string.charAt(i)+'\'' +" ; apilo la letra i-esima del string en HEAP");
            TablaSimbolos.instruccionesMaquina.add("STOREREF "+ i +" ; Guardo la letra apilada");
        }
        TablaSimbolos.instruccionesMaquina.add("DUP ; Duplico la referencia del comienzo del String ya que STOREREF la consumira para almacenar el proximo caracter");
        TablaSimbolos.instruccionesMaquina.add("PUSH "+ 0 +" ; Apilo el caracter terminador");
        TablaSimbolos.instruccionesMaquina.add("STOREREF "+ string.length() +" ; Guardo el caracter terminador en el heap. Indica que es el fin del String");
    }
}
