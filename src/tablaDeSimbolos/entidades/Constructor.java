package tablaDeSimbolos.entidades;

import analizadorLexico.Token;
import tablaDeSimbolos.tipos.TipoVoid;

public class Constructor extends Unidad{
    
    private Token tokenIdClase;
    

    public Constructor(Token tokenIdClase) throws ExcepcionSemantica{
        super();
        this.tokenIdClase = tokenIdClase;
        esDinamico = true;
        tipoUnidad = new TipoVoid();

        offsetDisponibleParametroFormal = 4; // Ya que FP esta apuntando a la primer varLocal en SP-1, en 1 esta ED, en 2 esta PR, y en 3 esta this.
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public String toString(){
        String signaturaConstructor = tokenIdClase.getLexema() + "(";
        for(ParametroFormal p : lista_parametrosFormales){
            signaturaConstructor += p.getTipo().getNombreTipo() + ",";
        }
        if(signaturaConstructor.charAt(signaturaConstructor.length() - 1) == ','){
            signaturaConstructor = signaturaConstructor.substring(0, signaturaConstructor.length() - 1);
        }
        signaturaConstructor += ")";
        return signaturaConstructor;
    }

    public String toStringLabel(){ // Lo hacemos asi ya que @ y $ son los simbolos validos en las etiquetas de CeIVM que no son validos para id's de constructores.
        String signaturaConstructor = tokenIdClase.getLexema() + "@";
        for(ParametroFormal p : lista_parametrosFormales){
            signaturaConstructor += p.getTipo().getNombreTipo() + "$";
        }
        if(signaturaConstructor.charAt(signaturaConstructor.length() - 1) == '$'){
            signaturaConstructor = signaturaConstructor.substring(0, signaturaConstructor.length() - 1);
        }
        signaturaConstructor += "@";

        return "l" + signaturaConstructor;
    }

    // Chequeo de declaraciones

    public void estaBienDeclarado() throws ExcepcionSemantica{
        super.estaBienDeclarado();
        if( !tokenIdClase.getLexema().equals(TablaSimbolos.claseActual.getTokenIdClase().getLexema()) ){
            throw new ExcepcionSemantica(tokenIdClase, "el constructor que se intenta crear no tiene el mismo nombre que la clase");
        }
    }

    // Generacion de codigo intermedio

    // Hecho en Unidad (clase padre)

}
