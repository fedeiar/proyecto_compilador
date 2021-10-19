package tablaDeSimbolos;

import tablaDeSimbolos.tipos.*;
import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Metodo extends Unidad{
    
    private Token tokenIdMet;
    private TipoDeToken formaMetodo;
    private TipoMetodo tipoMetodo;
    

    public Metodo(Token tokenIdMet, TipoDeToken formaMetodo, TipoMetodo tipoMetodo){
        super();
        this.tokenIdMet = tokenIdMet;
        this.formaMetodo = formaMetodo;
        this.tipoMetodo = tipoMetodo;
    }

    public Token getTokenIdMet(){
        return tokenIdMet;
    }

    public TipoDeToken getFormaMetodo(){
        return formaMetodo;
    }

    public TipoMetodo getTipoMetodo(){
        return tipoMetodo;
    }

    public boolean equalsSignatura(Metodo metodo){
        boolean mismaFormaMetodo = this.formaMetodo == metodo.getFormaMetodo();
        boolean mismoTipo = this.tipoMetodo.mismoTipo(metodo.getTipoMetodo());
        boolean mismoNombre = this.tokenIdMet.getLexema().equals(metodo.getTokenIdMet().getLexema());
        boolean mismosParametros = this.mismosParametros(metodo);
        return mismaFormaMetodo && mismoNombre && mismoTipo && mismosParametros;
    }

    public boolean redefineCorrectamente(Metodo metodoAncestro){
        boolean mismaFormaMetodo = this.formaMetodo == metodoAncestro.getFormaMetodo();
        boolean mismoTipo = this.tipoMetodo.verificarCompatibilidad(metodoAncestro.getTipoMetodo()); //USAR ESTO EN EL ORDEN ADECUADO!
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
