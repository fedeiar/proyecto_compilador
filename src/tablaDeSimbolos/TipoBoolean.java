package tablaDeSimbolos;

public class TipoBoolean extends TipoPrimitivo{
    
    public TipoBoolean(){

    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoBoolean tipo){
        return true;
    }
}
