package tablaDeSimbolos.nodosAST;

import java.util.List;
import java.util.ArrayList;

public class NodoBloque extends NodoSentencia{
    
    private List<NodoSentencia> sentencias;

    public NodoBloque(){
        sentencias = new ArrayList<>();
    }


    public void insertarSentencia(NodoSentencia sentencia){
        if(sentencia != null){ //TODO: está bien controlarlo aca cuando es <Sentencia>::= ;?
            sentencias.add(sentencia);
        }
    }
}
