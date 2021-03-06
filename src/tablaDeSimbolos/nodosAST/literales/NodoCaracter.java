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

    public void generarCodigo(){
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+tokenLitCaracter.getLexema()+" ; Apilo un literal caracter");
    }
}
