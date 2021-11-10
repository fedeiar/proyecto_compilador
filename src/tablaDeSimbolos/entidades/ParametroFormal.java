package tablaDeSimbolos.entidades;

import tablaDeSimbolos.tipos.*;
import analizadorLexico.Token;

public class ParametroFormal implements IVariable{
    
    private Token tokenIdVar;
    private TipoConcreto tipoParametro;

    private int offset;

    public ParametroFormal(Token tokenIdVar, TipoConcreto tipoParametro){
        this.tokenIdVar = tokenIdVar;
        this.tipoParametro = tipoParametro;

        this.offset = -1; 
    }

    public Token getTokenIdVar(){
        return tokenIdVar;
    }

    public TipoConcreto getTipo(){
        return tipoParametro;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica{
        tipoParametro.verificarExistenciaTipo();
    }

    public boolean mismoTipo(ParametroFormal parametroFormal){
        return tipoParametro.mismoTipo(parametroFormal.getTipo());
    }

    public String toString(){
        return tokenIdVar.getLexema();
    }
}
