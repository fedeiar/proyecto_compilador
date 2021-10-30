package tablaDeSimbolos.tipos;

public class TipoNull extends TipoConcreto{ //TODO: esta bien esta clase? con esto alcanza para siempre que asignemos null?
    

    public TipoNull(){
    }

    public String getNombreTipo(){
        return "null";
    }

    public boolean mismoTipo(Tipo tipo){ // TODO: esta bien?
        return tipo instanceof TipoClase;
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){ //TODO: esta bien?
        return tipoDelAncestro instanceof TipoClase;
    }
}
