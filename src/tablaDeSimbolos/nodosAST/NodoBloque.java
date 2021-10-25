package tablaDeSimbolos.nodosAST;

import java.util.List;
import java.util.Map;

import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;

import java.util.ArrayList;
import java.util.HashMap;

public class NodoBloque extends NodoSentencia{
    
    private List<NodoSentencia> sentencias;
    private Map<String, NodoVarLocal> varLocales; //TODO: esta bien esto? luego en la clase NodoAccesoVar, luego de crearse, le pide a la TS el bloque del tope y se inserta ahi?

    public NodoBloque(){
        sentencias = new ArrayList<>();
        varLocales = new HashMap<>();
    }


    public void insertarSentencia(NodoSentencia sentencia){
        if(sentencia != null){
            sentencias.add(sentencia);
        }
    }


    public void chequear() throws ExcepcionSemantica{
        TablaSimbolos.apilarBloqueActual(this); //TODO esta bien? entonces de esta manera tenemos siempre al tope el bloque más anidado.
        for(NodoSentencia sentencia : sentencias){
            sentencia.chequear();
        }
        //TODO
    }
}
