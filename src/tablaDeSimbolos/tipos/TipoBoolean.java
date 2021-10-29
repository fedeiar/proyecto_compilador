package tablaDeSimbolos.tipos;

public class TipoBoolean extends TipoPrimitivo{
    
    public TipoBoolean(){

    }

    public String getNombreTipo(){
        return "boolean";
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoBoolean tipo){
        return true;
    }
}
