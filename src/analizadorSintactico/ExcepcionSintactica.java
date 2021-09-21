package analizadorSintactico;

import analizadorLexico.*;

public class ExcepcionSintactica extends Exception {
    
    public ExcepcionSintactica(String nombreTokenEsperado, Token tokenActual){
        super(armarMensaje(nombreTokenEsperado, tokenActual));
    }

    private static String armarMensaje(String nombreTokenEsperado, Token tokenActual){
        return "Error sintactico en linea "+tokenActual.getNroLinea()+": se esperaba "+nombreTokenEsperado+" y se encontro "+ 
        tokenActual.getLexema()+"\n\n[Error:"+tokenActual.getLexema()+"|"+tokenActual.getNroLinea()+"]";
    }
}
