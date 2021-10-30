package tablaDeSimbolos.tipos;

import analizadorLexico.Token;
import tablaDeSimbolos.Clase;
import tablaDeSimbolos.TablaSimbolos;

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
        Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
        Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
        if(tokenClasePadreDelSubtipo != null){
            return 1 + profundidadDelHijo(new TipoClase(tokenClasePadreDelSubtipo));
        }else{
            return 0;
        }
    }
}
