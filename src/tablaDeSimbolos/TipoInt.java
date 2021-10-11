package tablaDeSimbolos;

public class TipoInt extends TipoPrimitivo{

    public TipoInt(){

    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoInt tipo){
        return true;
    }
}
