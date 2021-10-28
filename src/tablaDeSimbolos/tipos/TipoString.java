package tablaDeSimbolos.tipos;

public class TipoString extends TipoPrimitivo{
    

    public TipoString(){
        
    }

    public String getNombreTipo(){
        return "String";
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean esSubtipo(TipoMetodo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoString tipo){
        return true;
    }
}
