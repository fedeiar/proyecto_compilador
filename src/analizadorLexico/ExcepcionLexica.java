package analizadorLexico;

public class ExcepcionLexica extends Exception {

    public ExcepcionLexica(String lexema, int nroLinea, String mensaje, int nroColumna, String linea){
        super(procesarString(lexema, nroLinea, mensaje, nroColumna, linea));
    }

    private static String procesarString(String lexema, int nroLinea, String mensaje, int nroColumna, String linea){
        String detalle = "Detalle: ";
        String full_detalle = detalle + linea;
        String cursor = "";
        for (int i = 0; i < detalle.length() + nroColumna - 1; i++){
            if (full_detalle.charAt(i) == '\t'){
                cursor += '\t';
            }else{
                cursor += ' ';
            }
        }
        cursor += '^';

        return "Error léxico en linea "+nroLinea+": "+mensaje+" en la columna "+nroColumna+"\n"+full_detalle+"\n"+cursor+"\n[Error:"+lexema+"|"+nroLinea+"]\n";

    }
}
