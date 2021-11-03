package tablaDeSimbolos.nodosAST.nodosSentencia;

import tablaDeSimbolos.entidades.ExcepcionSemantica;

public class NodoSentenciaVacia extends NodoSentencia{

    
    public NodoSentenciaVacia(){

    }


    public void chequear() throws ExcepcionSemantica {
        // Siempre es correcto, no hacer nada
    }
    
}
