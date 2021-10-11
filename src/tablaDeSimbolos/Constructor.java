package tablaDeSimbolos;

import java.util.Map;

import analizadorLexico.Token;

public class Constructor extends Unidad{
    
    private Token tokenIdClase;

    public Constructor(Token tokenIdClase) throws ExcepcionSemantica{
        super();
        this.tokenIdClase = tokenIdClase;
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }


    public void estaBienDeclarado() throws ExcepcionSemantica{
        super.estaBienDeclarado();
        
        if( !tokenIdClase.getLexema().equals(TablaSimbolos.claseActual.getTokenIdClase().getLexema()) ){ //TODO: esta bien este chequeo?
            throw new ExcepcionSemantica(tokenIdClase, "el constructor que se intenta crear no tiene el mismo nombre que la clase");
        }
        //TODO: ver si pueden haber mas controles
    }
    
}
