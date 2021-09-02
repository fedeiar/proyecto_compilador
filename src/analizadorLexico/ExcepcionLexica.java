package analizadorLexico;

public class ExcepcionLexica extends Exception {

    public ExcepcionLexica(String lexema, int nroLinea, String mensaje){
        super("Error léxico en linea "+nroLinea+": "+mensaje+"\n \n[Error:"+lexema+"|"+nroLinea+"]");
    }
}
