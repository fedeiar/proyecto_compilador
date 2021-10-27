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
        return tipoDelAncestro.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoInt tipo){
        return true;
    }
}
