package tablaDeSimbolos.tipos;

public class TipoChar extends TipoPrimitivo{
    
    public TipoChar(){

    }

    public String getNombreTipo(){
        return "char";
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoChar tipo){
        return true;
    }
}
