package tablaDeSimbolos;
import java.util.HashMap;
import java.util.Map;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Metodo extends Unidad{
    
    private Token tokenIdMet;
    private TipoDeToken formaMetodo;
    private TipoMetodo tipoMetodo;
    

    public Metodo(Token tokenIdMet, TipoDeToken formaMetodo, TipoMetodo tipoMetodo){
        this.tokenIdMet = tokenIdMet;
        this.formaMetodo = formaMetodo;
        this.tipoMetodo = tipoMetodo;
    }

    public Token getTokenIdMet(){
        return tokenIdMet;
    }

    public TipoDeToken getFormaMetodo(){
        return formaMetodo;
    }

    public TipoMetodo getTipoMetodo(){
        return tipoMetodo;
    }

}
