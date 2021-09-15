package analizadorSintactico;

import analizadorLexico.TipoDeToken;
import analizadorLexico.*;

public class ExcepcionSintactica extends Exception {
    
    public ExcepcionSintactica(Token tokenActual, String nombreTokenEsperado){
        super(armarMensaje(tokenActual, nombreTokenEsperado));
    }

    private static String armarMensaje(Token tokenActual, String nombreTokenEsperado){
        return "se encontro "+tokenActual.getLexema()+" y se esperaba "+ nombreTokenEsperado;
    }
}
