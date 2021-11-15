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

        TablaSimbolos.instruccionesMaquina.add("RMEM 1");
        TablaSimbolos.instruccionesMaquina.add("PUSH "+ (string.length() + 1) );
        TablaSimbolos.instruccionesMaquina.add("PUSH simple_malloc");
        TablaSimbolos.instruccionesMaquina.add("CALL");
        for(int i = 0; i < string.length(); i++){
            TablaSimbolos.instruccionesMaquina.add("DUP");
            TablaSimbolos.instruccionesMaquina.add("PUSH "+'\''+string.charAt(i)+'\'');
            TablaSimbolos.instruccionesMaquina.add("STOREREF "+ i);
        }
        TablaSimbolos.instruccionesMaquina.add("DUP");
        TablaSimbolos.instruccionesMaquina.add("PUSH "+ 0);
        TablaSimbolos.instruccionesMaquina.add("STOREREF "+ string.length());
    }
}
