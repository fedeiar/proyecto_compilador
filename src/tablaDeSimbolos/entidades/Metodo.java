package tablaDeSimbolos.entidades;

import tablaDeSimbolos.tipos.*;

import java.time.OffsetDateTime;

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

        if(esDinamico){ //TODO: esta bien?
            offsetDisponibleRA = 4; // Ya que FP esta apuntando a dir(ED) - 1, en 1 esta ED, en 2 esta PR, y en 3 esta this. //TODO: esta bien?
        } else{
            offsetDisponibleRA = 3; // Ya que no tiene this.
        }
    }

    public Token getTokenIdMet(){
        return tokenIdMet;
    }

    public Token getTokenClaseContenedora(){
        return tokenClaseContenedora;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset = offset;
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


    public void generarCodigo(){ // TODO: esta bien? queda asi?
        
        TablaSimbolos.instruccionesMaquina.add("LOADFP"); // Guarda en la pila el enlace dinámico al comienzo del RA del llamador.
        TablaSimbolos.instruccionesMaquina.add("LOADSP"); // Apila el lugar donde comienza el RA de la unidad llamada
        TablaSimbolos.instruccionesMaquina.add("STOREFP"); // Actualiza el FP para que apunte al comienzo del RA de la unidad llamada.

        bloque.generarCodigo();

        if(this.esDinamico){ // TODO: esta bien? solo cambia ese +1?
            TablaSimbolos.instruccionesMaquina.add("STOREFP");
            TablaSimbolos.instruccionesMaquina.add("RET "+ lista_parametrosFormales.size() + 1); // Libera los n parametros de la pila + el this
        } else{ // Es estatico
            
            TablaSimbolos.instruccionesMaquina.add("STOREFP");
            TablaSimbolos.instruccionesMaquina.add("RET "+ lista_parametrosFormales.size()); // Libera los n parametros de la pila
        }
        
    }

}
