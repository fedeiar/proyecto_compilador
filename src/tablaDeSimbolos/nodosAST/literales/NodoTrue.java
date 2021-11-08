package tablaDeSimbolos.nodosAST.literales;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.tipos.*;

public class NodoTrue extends NodoBoolean{
    
    private Token tokenTrue;

    public NodoTrue(Token tokenTrue){
        this.tokenTrue = tokenTrue;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        return new TipoBoolean();
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        // TODO
    }
}
