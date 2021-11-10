package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.*;
public class NodoCaracter extends NodoOperando{
    
    private Token tokenLitCaracter;

    public NodoCaracter(Token tokenLitCaracter){
        this.tokenLitCaracter = tokenLitCaracter;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoChar();
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: va asi con la secuencia de escape y todo?
        TablaSimbolos.instruccionesMaquina.add("PUSH "+tokenLitCaracter.getLexema());
    }
}
