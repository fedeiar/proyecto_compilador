package tablaDeSimbolos.entidades;

import tablaDeSimbolos.nodosAST.nodosSentencia.NodoBloque;
import tablaDeSimbolos.tipos.*;


import analizadorLexico.Token;

public class Metodo extends Unidad{
    
    private Token tokenIdMet;
    private Token tokenClaseContenedora;
    private int offset; 

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

    public boolean perteneceAClase(Token tokenIdClase){
        return tokenClaseContenedora.getLexema().equals(tokenIdClase.getLexema());
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

    public String toStringLabel(){ // Lo hacemos asi ya que @ y $ son los simbolos validos en las etiquetas de CeIVM que no son validos para id's de metodos.
        String signaturaMetodo = tokenIdMet.getLexema() + "@";
        for(ParametroFormal p : lista_parametrosFormales){
            signaturaMetodo += p.getTipo().getNombreTipo() + "$";
        }
        if(signaturaMetodo.charAt(signaturaMetodo.length() - 1) == '$'){
            signaturaMetodo = signaturaMetodo.substring(0, signaturaMetodo.length() - 1);
        }
        signaturaMetodo += "@";

        return "l" + signaturaMetodo + this.getTokenClaseContenedora().getLexema();
    }


    public void generarCodigo(){ // TODO: esta bien?
        
        TablaSimbolos.instruccionesMaquina.add("LOADFP");
        TablaSimbolos.instruccionesMaquina.add("LOADSP");
        TablaSimbolos.instruccionesMaquina.add("STOREFP");

        bloque.generarCodigo();

        if(this.esDinamico){
            //TODO: hacer
        } else{ // Es estatico
            
            TablaSimbolos.instruccionesMaquina.add("STOREFP");
            TablaSimbolos.instruccionesMaquina.add("RET "+ lista_parametrosFormales.size()); 
        }
        
    }

}
