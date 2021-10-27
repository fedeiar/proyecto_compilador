package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;

public abstract class NodoExpresion {
 
    public abstract Tipo chequear() throws ExcepcionSemantica; //TODO: esta bien que devuelva el tipo?

    public abstract Token getToken(); //TODO: esta bien esto? lo necesito para saber la linea en error por ejemplo del if.
}
