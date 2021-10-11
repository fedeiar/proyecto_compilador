package tablaDeSimbolos;

import analizadorLexico.Token;

public class TipoClase extends Tipo{
    

    private Token tokenIdClase;

    public TipoClase(Token tokenIdClase){
        this.tokenIdClase = tokenIdClase;
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ //TODO: preg si está bien
        if(!TablaSimbolos.getInstance().existeClase(tokenIdClase.getLexema())){
            throw new ExcepcionSemantica(tokenIdClase, "la clase "+tokenIdClase.getLexema()+" no esta declarada");
        }
    }

    public boolean mismoTipo(TipoMetodo tipo){ //TODO: como puedo hacer mejor esto, sin usar instanceof
        if(tipo instanceof TipoClase){
            TipoClase tipo_param = (TipoClase) tipo;
            return this.tokenIdClase.getLexema().equals(tipo_param.getTokenIdClase().getLexema());
        } else{
            return false;
        }
    }

    public boolean verCompatibilidad(TipoClase tipo){
        //TODO: implementar
        return true;
    }
    
}
