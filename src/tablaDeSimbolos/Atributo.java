package tablaDeSimbolos;
import tablaDeSimbolos.tipos.*;
import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Atributo{
    
    private Token tokenIdVar;
    private TipoDeToken visibilidadAtributo;
    private Tipo tipoAtributo;
    
    public Atributo(Token idVar, TipoDeToken visibilidadAtributo, Tipo tipoAtributo){
        this.tokenIdVar = idVar;
        this.tipoAtributo = tipoAtributo;
        this.visibilidadAtributo = visibilidadAtributo;
    }

    public Token getTokenIdVar(){
        return tokenIdVar;
    }

    public TipoDeToken getVisibilidadAtributo(){
        return visibilidadAtributo;
    }

    public boolean esPublic(){ //TODO: asi estaria bien para saber si es public o private?
        return visibilidadAtributo.toString().equals("pr_public");
    }

    public Tipo getTipo(){
        return tipoAtributo;
    }


    public void estaBienDeclarado() throws ExcepcionSemantica{
        tipoAtributo.verificarExistenciaTipo();
    }
}
