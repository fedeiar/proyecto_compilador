package tablaDeSimbolos.tipos;

public class TipoString extends TipoPrimitivo{
    

    public TipoString(){
        
    }

    public String getNombreTipo(){
        return "String";
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoString tipo){
        return true;
    }
}
