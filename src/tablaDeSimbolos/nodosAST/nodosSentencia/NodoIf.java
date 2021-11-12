package tablaDeSimbolos.nodosAST.nodosSentencia;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.TipoBoolean;

public class NodoIf extends NodoSentencia{
    
    private Token tokenIf;
    private NodoExpresion NodoExpresionCondicion;
    private NodoSentencia nodoSentenciaIf;
    private NodoSentencia nodoSentenciaElse;

    public NodoIf(Token tokenIf, NodoExpresion condicion, NodoSentencia sentenciaIf){
        this.tokenIf = tokenIf;
        this.NodoExpresionCondicion = condicion;
        this.nodoSentenciaIf = sentenciaIf;
    }


    public void insertarSentenciaElse(NodoSentencia nodoSentenciaElse){
        this.nodoSentenciaElse = nodoSentenciaElse;
    }

    public void chequear() throws ExcepcionSemantica{

        if(NodoExpresionCondicion.chequear().mismoTipo(new TipoBoolean())){ 
            NodoBloque nodoBloqueIf = new NodoBloque(); // Al tener este bloque ficticio, en caso de que el nodoSentenciaIf (o else) sea una declaración de varLocal, se insertará aca y luego quitada.
            TablaSimbolos.apilarBloqueActual(nodoBloqueIf);
            
            nodoSentenciaIf.chequear();
            
            TablaSimbolos.desapilarBloqueActual(); // Aca se quitan las variables locales declaradas en caso de que la sentencia no sea un bloque.

            if(nodoSentenciaElse != null){
                NodoBloque nodoBloqueElse = new NodoBloque(); // Hacemos un bloque ficticio aparte para el else
                TablaSimbolos.apilarBloqueActual(nodoBloqueElse);

                nodoSentenciaElse.chequear();

                TablaSimbolos.desapilarBloqueActual(); // Aca se quitan las variables locales del else.
            }
        } else{
            throw new ExcepcionSemantica(tokenIf, "se esperaba una expresion de tipo boolean");
        }

    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        //TODO
        //OJO: puede pasar algo parecido a lo del for con el tema de las variables locales.
    }
}
