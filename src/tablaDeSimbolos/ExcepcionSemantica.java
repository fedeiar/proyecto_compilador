package tablaDeSimbolos;

import analizadorLexico.Token;

public class ExcepcionSemantica extends Exception{

    public ExcepcionSemantica(Token tokenActual, String mensaje){ //TODO: fijarse bien los parametros, que se necesita
        super(armarMensaje(tokenActual, mensaje)); 
    }

    private static String armarMensaje(Token tokenActual, String mensaje){
        return "Error semantico en linea "+tokenActual.getNroLinea()+": " + mensaje +
        "\n\n[Error:"+tokenActual.getLexema()+"|"+tokenActual.getNroLinea()+"]";
    }
}
