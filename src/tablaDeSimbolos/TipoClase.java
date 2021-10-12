package tablaDeSimbolos;

import analizadorLexico.Token;

public class TipoClase extends Tipo{
    

    private Token tokenIdClase;

    public TipoClase(Token tokenIdClase){
        this.tokenIdClase = tokenIdClase;
    }

    public String getNombreTipo(){
        return tokenIdClase.getLexema();
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ //TODO: preg si está bien
        if(!TablaSimbolos.getInstance().existeClase(tokenIdClase.getLexema())){
            throw new ExcepcionSemantica(tokenIdClase, "la clase "+tokenIdClase.getLexema()+" no esta declarada");
        }
    }

    public boolean mismoTipo(TipoMetodo tipo){ //TODO: esta bien hecho asi?
        return tipo.visitarMismoTipo(this);
    }

    public boolean visitarMismoTipo(TipoClase tipo){ //TODO: esta bien hecho asi?
        return this.tokenIdClase.getLexema().equals(tipo.getTokenIdClase().getLexema());
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoClase subtipo){ //TODO: preguntar si esta bien.
        if(this.tokenIdClase.getLexema().equals(subtipo.getTokenIdClase().getLexema())){
            return true;
        } else {
            Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
            Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
            if(tokenClasePadreDelSubtipo != null){
                return VisitarVerCompatibilidad(new TipoClase(tokenClasePadreDelSubtipo)); //TODO: esta mal esto?
            }else{
                return false;
            }
        }
    }

    
    
}
