package tablaDeSimbolos;

public class TipoChar extends TipoPrimitivo{
    
    public TipoChar(){

    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoChar tipo){
        return true;
    }
}
