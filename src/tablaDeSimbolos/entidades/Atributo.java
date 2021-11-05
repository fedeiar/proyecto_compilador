package tablaDeSimbolos.entidades;
import tablaDeSimbolos.tipos.*;
import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Atributo{
    
    private Token tokenIdVar;
    private Token tokenClaseContenedora; 
    private TipoDeToken visibilidadAtributo;
    private TipoConcreto tipoAtributo;
    
    
    public Atributo(Token idVar, TipoDeToken visibilidadAtributo, TipoConcreto tipoAtributo, Token tokenClaseContenedora){
        this.tokenIdVar = idVar;
        this.tipoAtributo = tipoAtributo;
        this.visibilidadAtributo = visibilidadAtributo;
        this.tokenClaseContenedora = tokenClaseContenedora; 
    }

    public Token getTokenIdVar(){
        return tokenIdVar;
    }

    public Token getTokenClaseContenedora(){ 
        return tokenClaseContenedora;
    }

    public TipoDeToken getVisibilidadAtributo(){
        return visibilidadAtributo;
    }

    public boolean esPublic(){ 
        return visibilidadAtributo == TipoDeToken.pr_public;
    }

    public TipoConcreto getTipo(){
        return tipoAtributo;
    }


    public void estaBienDeclarado() throws ExcepcionSemantica{
        tipoAtributo.verificarExistenciaTipo();
    }
}
