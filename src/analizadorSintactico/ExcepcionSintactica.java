package analizadorSintactico;

import analizadorLexico.TipoDeToken;
import analizadorLexico.*;

public class ExcepcionSintactica extends Exception {
    
    public ExcepcionSintactica(Token tokenActual, String nombreTokenEsperado){
        super(armarMensaje(tokenActual, nombreTokenEsperado));
    }

    private static String armarMensaje(Token tokenActual, String nombreTokenEsperado){
        return "Error sintactico en linea "+tokenActual.getNroLinea()+": se encontro "+tokenActual.getLexema()+" y se esperaba "+ 
        nombreTokenEsperado+"\n\n[Error:"+tokenActual.getLexema()+"|"+tokenActual.getNroLinea()+"]";
    }
}
