package tablaDeSimbolos.nodosAST;

import java.util.List;
import java.util.ArrayList;

public class NodoBloque extends NodoSentencia{
    
    private List<NodoSentencia> sentencias;

    public NodoBloque(){
        sentencias = new ArrayList<>();
    }


    public void insertarSentencia(NodoSentencia sentencia){
        sentencias.add(sentencia);
    }
}
