package tablaDeSimbolos.tipos;

public class TipoChar extends TipoPrimitivo{
    
    public TipoChar(){

    }

    public String getNombreTipo(){
        return "char";
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean esSubtipo(TipoMetodo tipoDelAncestro){
        return tipoDelAncestro.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoChar tipo){
        return true;
    }
}
