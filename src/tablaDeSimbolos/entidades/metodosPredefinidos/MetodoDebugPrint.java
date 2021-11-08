package tablaDeSimbolos.entidades.metodosPredefinidos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Metodo;
import tablaDeSimbolos.tipos.Tipo;

public class MetodoDebugPrint extends Metodo{ //TODO: esta bien esta clase?
    
    public MetodoDebugPrint(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super(tokenIdMet, esDinamico, tipoMetodo, claseContenedora);
    }


    public void generarCodigo(){
        // TODO: redefinir.
    }
}
