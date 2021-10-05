package tablaDeSimbolos;

import analizadorLexico.Token;

public class ExcepcionSemantica extends Exception{

    public ExcepcionSemantica(Token tokenActual){ //TODO: fijarse bien los parametros, que se necesita
        super(armarMensaje(tokenActual)); 
    }

    private static String armarMensaje(Token tokenActual){
        return "Error semantico en linea "+tokenActual.getNroLinea()+": " + //TODO: completar aca el error
        "\n\n[Error:"+tokenActual.getLexema()+"|"+tokenActual.getNroLinea()+"]";
    }
}
