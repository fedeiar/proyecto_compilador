package tablaDeSimbolos;

public class TipoString extends TipoPrimitivo{
    

    public TipoString(){
        
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoString tipo){
        return true;
    }
}
