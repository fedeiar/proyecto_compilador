package tablaDeSimbolos.tipos;

public class TipoInt extends TipoPrimitivo{

    public TipoInt(){

    }

    public String getNombreTipo(){
        return "int";
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean esSubtipo(TipoMetodo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoInt tipo){
        return true;
    }
}
