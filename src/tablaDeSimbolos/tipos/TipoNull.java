package tablaDeSimbolos.tipos;

public class TipoNull extends TipoConcreto{
    

    public TipoNull(){
    }

    public String getNombreTipo(){
        return "null";
    }

    public boolean mismoTipo(Tipo tipo){
        return tipo instanceof TipoClase;
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){
        return tipoDelAncestro instanceof TipoClase;
    }

    public int profundidadDelHijo(TipoClase subtipo){ //TODO: preguntar si esta bien.
        if(this.tokenIdClase.getLexema().equals(subtipo.getTokenIdClase().getLexema())){
            return 0;
        } else {
            Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
            Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
            if(tokenClasePadreDelSubtipo != null){
                return 1 + profundidadDelHijo(new TipoClase(tokenClasePadreDelSubtipo));
            }else{
                return -1;
            }
        }
    }
}
