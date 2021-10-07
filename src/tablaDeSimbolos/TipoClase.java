package tablaDeSimbolos;

import analizadorLexico.Token;

public class TipoClase extends Tipo{
    

    private Token tokenIdClase;

    public TipoClase(Token tokenIdClase){
        //TODO: aca debería verificarse que exista la clase asociada? no, ese tipo de chequeos se realiza después del analisis sintactico.
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public boolean verCompatibilidad(TipoClase tipo){
        //TODO
    }
    
}
