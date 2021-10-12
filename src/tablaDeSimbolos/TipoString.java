package tablaDeSimbolos;

public class TipoString extends TipoPrimitivo{
    

    public TipoString(){
        
    }

    public String getNombreTipo(){
        return "String";
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoString tipo){
        return true;
    }
}
