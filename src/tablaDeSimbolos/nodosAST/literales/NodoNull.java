package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;
import tablaDeSimbolos.tipos.*;

public class NodoNull extends NodoOperando{

    private Token tokenNull;

    public NodoNull(Token tokenNull){
        this.tokenNull = tokenNull;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoNull(); 
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        TablaSimbolos.instruccionesMaquina.add("PUSH "+ 0);
    }
}
