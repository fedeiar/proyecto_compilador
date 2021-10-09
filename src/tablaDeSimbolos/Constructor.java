package tablaDeSimbolos;

import java.util.Map;

import analizadorLexico.Token;

public class Constructor extends Unidad{
    
    private Token tokenIdClase;

    public Constructor(Token tokenIdClase) throws ExcepcionSemantica{
        this.tokenIdClase = tokenIdClase;
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }


    public void estaBienDeclarado() throws ExcepcionSemantica{
        super.estaBienDeclarado();
        if( !tokenIdClase.getLexema().equals(TablaSimbolos.getInstance().claseActual.getTokenIdClase().getLexema()) ){
            throw new ExcepcionSemantica(tokenIdClase);
        }
        //TODO: ver si pueden haber mas controles
    }
    
}
