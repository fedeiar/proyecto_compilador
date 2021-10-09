package tablaDeSimbolos;

import analizadorLexico.Token;

public class TipoClase extends Tipo{
    

    private Token tokenIdClase;

    public TipoClase(Token tokenIdClase){
        //TODO: aca debería verificarse que exista la clase asociada? no, ese tipo de chequeos se realiza después del analisis sintactico.
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ //TODO: preg si está bien
        if(!TablaSimbolos.getInstance().existeClase(tokenIdClase.getLexema())){
            throw new ExcepcionSemantica(tokenIdClase, "la clase "+tokenIdClase.getLexema()+" no esta declarada");
        }
    }

    public boolean verCompatibilidad(TipoClase tipo){
        //TODO
    }
    
}
