package tablaDeSimbolos;

import tablaDeSimbolos.tipos.*;
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

    public Tipo getTipo(){
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
