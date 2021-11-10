package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.tipos.TipoBoolean;
import tablaDeSimbolos.tipos.TipoConcreto;

public class NodoFalse extends NodoBoolean{
    
    private Token tokenFalse;

    public NodoFalse(Token tokenFalse){
        this.tokenFalse = tokenFalse;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoBoolean();
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){ //TODO: esta bien asi?
        TablaSimbolos.instruccionesMaquina.add("PUSH "+ 1);
    }
}
