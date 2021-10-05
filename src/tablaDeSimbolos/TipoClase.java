package tablaDeSimbolos;

import analizadorLexico.Token;

public class TipoClase extends Tipo{
    

    private Token tokenIdClase;

    public TipoClase(Token tokenIdClase){
        esPrimitivo = false;
        //TODO: aca debería verificarse que exista la clase asociada? no, ese tipo de chequeos se realiza después del analisis sintactico.
    }

    //TODO: un metodo para obtener el nombre de la clase
}
