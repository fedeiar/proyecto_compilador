package tablaDeSimbolos;

import tablaDeSimbolos.tipos.*;
import analizadorLexico.Token;

public class Metodo extends Unidad{
    
    private Token tokenIdMet;
    private TipoMetodo tipoMetodo;
    //TODO: un atributo que indique a la clase a la cual pertenece


    public Metodo(Token tokenIdMet, boolean esDinamico, TipoMetodo tipoMetodo){
        super();
        this.tokenIdMet = tokenIdMet;
        this.esDinamico = esDinamico;
        this.tipoMetodo = tipoMetodo;
    }

    public Token getTokenIdMet(){
        return tokenIdMet;
    }

    public TipoMetodo getTipoMetodo(){
        return tipoMetodo;
    }

    

    public boolean equalsSignatura(Metodo metodo){
        boolean mismaFormaMetodo = this.esDinamico == metodo.esDinamico();
        boolean mismoTipo = this.tipoMetodo.mismoTipo(metodo.getTipoMetodo());
        boolean mismoNombre = this.tokenIdMet.getLexema().equals(metodo.getTokenIdMet().getLexema());
        boolean mismosParametros = this.mismosParametros(metodo);
        return mismaFormaMetodo && mismoNombre && mismoTipo && mismosParametros;
    }

    public boolean redefineCorrectamente(Metodo metodoAncestro){
        boolean mismaFormaMetodo = this.esDinamico == metodoAncestro.esDinamico();
        boolean mismoTipo = this.tipoMetodo.esSubtipo(metodoAncestro.getTipoMetodo());
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
        for(ParametroFormal p : lista_parametros){
            signaturaMetodo += p.getTipo().getNombreTipo() + ",";
        }
        if(signaturaMetodo.charAt(signaturaMetodo.length() - 1) == ','){
            signaturaMetodo = signaturaMetodo.substring(0, signaturaMetodo.length() - 1);
        }
        signaturaMetodo += ")";
        return signaturaMetodo;
    }

}
