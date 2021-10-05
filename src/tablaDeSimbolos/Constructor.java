package tablaDeSimbolos;

import java.util.Map;

import analizadorLexico.Token;

public class Constructor {
    
    private Token tokenIdClase;
    private Map<String, ParametroFormal> parametros;

    public Constructor(Token tokenIdClase){
        this.tokenIdClase = tokenIdClase;
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public void insertarParametro(String nombreParametro, ParametroFormal parametro) throws ExcepcionSemantica{
        if(parametros.get(nombreParametro) == null){
            parametros.put(nombreParametro, parametro);
        } else{
            throw new ExcepcionSemantica(parametro.getTokenIdVar());
        }
    }
}
