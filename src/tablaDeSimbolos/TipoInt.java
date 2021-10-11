package tablaDeSimbolos;

public class TipoInt extends TipoPrimitivo{

    public TipoInt(){

    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoInt tipo){
        return true;
    }
}
