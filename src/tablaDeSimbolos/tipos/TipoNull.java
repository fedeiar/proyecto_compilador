package tablaDeSimbolos.tipos;

import analizadorLexico.Token;

public class TipoNull extends Tipo{ //TODO: esta bien esta clase? con esto alcanza para siempre que asignemos null?
    

    public TipoNull(){
    }

    public String getNombreTipo(){
        return "null";
    }

    public boolean mismoTipo(TipoMetodo tipo){ // TODO: esta bien?
        return tipo instanceof TipoClase;
    }

    public boolean esSubtipo(TipoMetodo tipoDelAncestro){ //TODO: esta bien?
        return tipoDelAncestro instanceof TipoClase;
    }
}
