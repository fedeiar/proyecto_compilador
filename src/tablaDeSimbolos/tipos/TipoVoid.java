package tablaDeSimbolos.tipos;

public class TipoVoid extends Tipo{
    
    public TipoVoid(){
        
    }

    public String getNombreTipo(){
        return "void";
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoVoid tipo){
        return true;
    }
}
