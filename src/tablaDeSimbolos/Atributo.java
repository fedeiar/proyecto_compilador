package tablaDeSimbolos;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Atributo {
    
    private Token tokenIdVar;
    private Tipo tipoAtributo;
    private TipoDeToken visibilidadAtributo;

    public Atributo(Token idVar, Tipo tipoAtributo, TipoDeToken visibilidadAtributo){
        this.tokenIdVar = idVar;
        this.tipoAtributo = tipoAtributo;
        this.visibilidadAtributo = visibilidadAtributo;
    }

    public Token getTokenIdVar(){
        return tokenIdVar;
    }
}
