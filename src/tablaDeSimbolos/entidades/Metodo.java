package tablaDeSimbolos.entidades;

import tablaDeSimbolos.tipos.*;


import analizadorLexico.Token;

public class Metodo extends Unidad{
    
    private Token tokenIdMet;
    private Token tokenClaseContenedora;
    private int offset; // TODO: esta bien?

    public Metodo(Token tokenIdMet, boolean esDinamico, Tipo tipoMetodo, Token claseContenedora){
        super();
        this.tokenIdMet = tokenIdMet;
        this.esDinamico = esDinamico;
        this.tipoUnidad = tipoMetodo;
        this.tokenClaseContenedora = claseContenedora;
        
        this.offset = -1; // Ya que inicialmente no conocemos el offset
    }

    public Token getTokenIdMet(){
        return tokenIdMet;
    }

    public Token getTokenClaseContenedora(){
        return tokenClaseContenedora;
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

    public boolean equalsSignatura(Metodo metodo){
        boolean mismaFormaMetodo = this.esDinamico == metodo.esDinamico();
        boolean mismoTipo = this.tipoUnidad.mismoTipo(metodo.getTipoUnidad());
        boolean mismoNombre = this.tokenIdMet.getLexema().equals(metodo.getTokenIdMet().getLexema());
        boolean mismosParametros = this.mismosParametros(metodo);
        return mismaFormaMetodo && mismoNombre && mismoTipo && mismosParametros;
    }

    public boolean redefineCorrectamente(Metodo metodoAncestro){
        boolean mismaFormaMetodo = this.esDinamico == metodoAncestro.esDinamico();
        boolean mismoTipo = this.tipoUnidad.esSubtipo(metodoAncestro.getTipoUnidad());
        boolean mismoNombre = this.tokenIdMet.getLexema().equals(metodoAncestro.getTokenIdMet().getLexema());
        boolean mismosParametros = this.mismosParametros(metodoAncestro);
        return mismaFormaMetodo && mismoNombre && mismoTipo && mismosParametros;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica{
        super.estaBienDeclarado();
        tipoUnidad.verificarExistenciaTipo();
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

    public String toStringLabel(){
        return "l" + this.toString() + this.getTokenClaseContenedora().getLexema();
    }


    public void generarCodigo(){ // TODO: esta bien?
        /*
        TablaSimbolos.instruccionesMaquina.add("LOADFP");
        TablaSimbolos.instruccionesMaquina.add("LOADSP");
        TablaSimbolos.instruccionesMaquina.add("STOREFP");

        // TODO: aca habría que pedirle al NodoBloque que genereCodigo()?

        if(this.esDinamico){

        } else{ // Es estatico
            if(!tipoUnidad.mismoTipo(new TipoVoid())){
                TablaSimbolos.instruccionesMaquina.add("STORE "+ lista_parametrosFormales.size() + 2); // + 2 por el PR y ED (no tiene this)
            }
            TablaSimbolos.instruccionesMaquina.add("STOREPF");
            TablaSimbolos.instruccionesMaquina.add("RET "+ lista_parametrosFormales.size()); 
        }
        */
    }

}
