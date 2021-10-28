package tablaDeSimbolos.tipos;

public class TipoBoolean extends TipoPrimitivo{
    
    public TipoBoolean(){

    }

    public String getNombreTipo(){
        return "boolean";
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean esSubtipo(TipoMetodo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoBoolean tipo){
        return true;
    }
}
