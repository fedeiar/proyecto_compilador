package tablaDeSimbolos.tipos;

public class TipoVoid extends TipoMetodo{
    
    public TipoVoid(){
        
    }

    public String getNombreTipo(){
        return "void";
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean esSubtipo(TipoMetodo tipoDelAncestro){
        return tipoDelAncestro.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoVoid tipo){
        return true;
    }
}
