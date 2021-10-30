package tablaDeSimbolos;

import tablaDeSimbolos.tipos.*;

import java.util.List;

import analizadorLexico.Token;

public class Metodo extends Unidad{
    
    private Token tokenIdMet;
    private Tipo tipoMetodo;
    private Token tokenClaseContenedora;


    public Metodo(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super();
        this.tokenIdMet = tokenIdMet;
        this.esDinamico = esDinamico;
        this.tipoMetodo = tipoMetodo;
        this.tokenClaseContenedora = claseContenedora;
    }

    public Token getTokenIdMet(){
        return tokenIdMet;
    }

    public Tipo getTipoUnidad(){
        return tipoMetodo;
    }

    public Token getTokenClaseContenedora(){
        return tokenClaseContenedora;
    }

    public boolean equalsSignatura(Metodo metodo){
        boolean mismaFormaMetodo = this.esDinamico == metodo.esDinamico();
        boolean mismoTipo = this.tipoMetodo.mismoTipo(metodo.getTipoUnidad());
        boolean mismoNombre = this.tokenIdMet.getLexema().equals(metodo.getTokenIdMet().getLexema());
        boolean mismosParametros = this.mismosParametros(metodo);
        return mismaFormaMetodo && mismoNombre && mismoTipo && mismosParametros;
    }

    public boolean redefineCorrectamente(Metodo metodoAncestro){
        boolean mismaFormaMetodo = this.esDinamico == metodoAncestro.esDinamico();
        boolean mismoTipo = this.tipoMetodo.esSubtipo(metodoAncestro.getTipoUnidad());
        boolean mismoNombre = this.tokenIdMet.getLexema().equals(metodoAncestro.getTokenIdMet().getLexema());
        boolean mismosParametros = this.mismosParametros(metodoAncestro);
        return mismaFormaMetodo && mismoNombre && mismoTipo && mismosParametros;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica{
        super.estaBienDeclarado();
        tipoMetodo.verificarExistenciaTipo();
    }

    public String toString(){
        String signaturaMetodo = tokenIdMet.getLexema() + "(";
        for(ParametroFormal p : lista_parametrosFormales){
            signaturaMetodo += p.getTipo().getNombreTipo() + ",";
        }
        if(signaturaMetodo.charAt(signaturaMetodo.length() - 1) == ','){
            signaturaMetodo = signaturaMetodo.substring(0, signaturaMetodo.length() - 1);
        }
        signaturaMetodo += ")";
        return signaturaMetodo;
    }

}
