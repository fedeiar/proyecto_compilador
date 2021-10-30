package tablaDeSimbolos.entidades;

import tablaDeSimbolos.tipos.*;
import analizadorLexico.Token;

public class ParametroFormal {
    
    private Token tokenIdVar;
    private TipoConcreto tipoParametro;

    public ParametroFormal(Token tokenIdVar, TipoConcreto tipoParametro){
        this.tokenIdVar = tokenIdVar;
        this.tipoParametro = tipoParametro;
    }

    public Token getTokenIdVar(){
        return tokenIdVar;
    }

    public TipoConcreto getTipo(){
        return tipoParametro;
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
