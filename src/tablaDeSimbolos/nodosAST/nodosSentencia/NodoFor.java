package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoBoolean;
import tablaDeSimbolos.entidades.TablaSimbolos;

public class NodoFor extends NodoSentencia{
    
    private Token tokenFor;
    private NodoVarLocal nodoVarLocal;
    private NodoExpresion nodoExpresionBooleana;
    private NodoAsignacion nodoAsignacion;
    private NodoSentencia nodoSentencia;

    public NodoFor(Token tokenFor, NodoVarLocal nodoVarLocal, NodoExpresion nodoExpresion, NodoAsignacion nodoAsignacion, NodoSentencia nodoSentencia){
        this.tokenFor = tokenFor;
        this.nodoVarLocal = nodoVarLocal;
        this.nodoExpresionBooleana = nodoExpresion;
        this.nodoAsignacion = nodoAsignacion;
        this.nodoSentencia = nodoSentencia;
    }

    public void chequear() throws ExcepcionSemantica{ 
        NodoBloque nodoBloqueFor = new NodoBloque(); // Idem a If, la declaración de la varLocal se insertará aca asi luego no es visible fuera del for.
        TablaSimbolos.apilarBloqueActual(nodoBloqueFor);
        
        nodoVarLocal.chequear();
        if(! nodoExpresionBooleana.chequear().mismoTipo(new TipoBoolean())){
            throw new ExcepcionSemantica(tokenFor, "la expresión de condicion del for debe ser de tipo boolean");
        }
        nodoAsignacion.chequear();
        nodoSentencia.chequear();

        TablaSimbolos.desapilarBloqueActual();
        
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        //TODO
    }

    
}
