package tablaDeSimbolos.entidades;
import tablaDeSimbolos.tipos.*;
import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Atributo implements IVariable{
    
    private Token tokenIdVar;
    private Token tokenClaseContenedora; 
    private TipoDeToken visibilidadAtributo;
    private TipoConcreto tipoAtributo;
    private int offset;
    
    
    public Atributo(Token idVar, TipoDeToken visibilidadAtributo, TipoConcreto tipoAtributo, Token tokenClaseContenedora){
        this.tokenIdVar = idVar;
        this.tipoAtributo = tipoAtributo;
        this.visibilidadAtributo = visibilidadAtributo;
        this.tokenClaseContenedora = tokenClaseContenedora;

        this.offset = -1; // Ya que inicialmente no conocemos el offset.
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

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

    public boolean tieneOffsetAsignado(){
        return offset != -1;
    }

    // Chequeo de declaraciones

    public void estaBienDeclarado() throws ExcepcionSemantica{
        tipoAtributo.verificarExistenciaTipo();
    }
}
