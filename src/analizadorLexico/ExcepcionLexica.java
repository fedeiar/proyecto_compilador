package analizadorLexico;

public class ExcepcionLexica extends Exception {

    public ExcepcionLexica(String lexema, int nroLinea, String mensaje, int nroColumna){
        super("Error léxico en linea "+nroLinea+": "+mensaje+" en la columna "+nroColumna+"\n \n[Error:"+lexema+"|"+nroLinea+"]");
    }
}
