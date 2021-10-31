package tablaDeSimbolos.entidades;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.TipoVoid;

public class Constructor extends Unidad{
    
    private Token tokenIdClase;
    

    public Constructor(Token tokenIdClase) throws ExcepcionSemantica{
        super();
        this.tokenIdClase = tokenIdClase;
        esDinamico = true;
        tipoUnidad = new TipoVoid(); //TODO: esto está bien? los constructores tienen return void?
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public void estaBienDeclarado() throws ExcepcionSemantica{
        super.estaBienDeclarado();
        
        if( !tokenIdClase.getLexema().equals(TablaSimbolos.claseActual.getTokenIdClase().getLexema()) ){
            throw new ExcepcionSemantica(tokenIdClase, "el constructor que se intenta crear no tiene el mismo nombre que la clase");
        }
    }

    public String toString(){
        String signaturaMetodo = tokenIdClase.getLexema() + "(";
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
