package tablaDeSimbolos;
import java.util.HashMap;
import java.util.Map;

import analizadorLexico.Token;

public class Metodo {
    
    private Token tokenIdMet;
    private String formaMetodo;
    private TipoMetodo tipoMetodo;
    private Map<String, ParametroFormal> parametros;


    public Metodo(Token tokenIdMet, String formaMetodo, TipoMetodo tipoMetodo){
        this.tokenIdMet = tokenIdMet;
        this.formaMetodo = formaMetodo;
        this.tipoMetodo = tipoMetodo;
    }

    public Token getTokenIdMet(){
        return tokenIdMet;
    }

    public String getFormaMetodo(){
        return formaMetodo;
    }

    public TipoMetodo getTipoMetodo(){
        return tipoMetodo;
    }

    public void insertarParametro(String nombreParametro, ParametroFormal parametro) throws ExcepcionSemantica{
        if(parametros.get(nombreParametro) == null){
            parametros.put(nombreParametro, parametro);
        } else{
            throw new ExcepcionSemantica(parametro.getTokenIdVar());
        }
    }
}
