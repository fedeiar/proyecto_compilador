package tablaDeSimbolos.tipos;

public class TipoInt extends TipoPrimitivo{

    public TipoInt(){

    }

    public String getNombreTipo(){
        return "int";
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoInt tipo){
        return true;
    }
}
