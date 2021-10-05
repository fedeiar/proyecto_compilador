package tablaDeSimbolos;

import analizadorLexico.Token;

public class ParametroFormal {
    
    private Token tokenIdVar;
    private Tipo tipoParametro;

    public ParametroFormal(Token tokenIdVar, Tipo tipoParametro){
        this.tokenIdVar = tokenIdVar;
        this.tipoParametro = tipoParametro;
    }

    public Token getTokenIdVar(){
        return tokenIdVar;
    }
}
