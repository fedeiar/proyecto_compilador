package tablaDeSimbolos;

import analizadorLexico.Token;

public class Atributo {
    
    private Token tokenIdVar;
    private Tipo tipoAtributo;
    private String visibilidadAtributo;

    public Atributo(Token idVar, Tipo tipoAtributo, String visibilidadAtributo){
        this.tokenIdVar = idVar;
        this.tipoAtributo = tipoAtributo;
        this.visibilidadAtributo = visibilidadAtributo;
    }

    public Token getTokenIdVar(){
        return tokenIdVar;
    }
}
