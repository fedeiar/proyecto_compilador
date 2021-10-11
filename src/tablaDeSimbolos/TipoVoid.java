package tablaDeSimbolos;

public class TipoVoid extends TipoMetodo{
    
    public TipoVoid(){
        
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoVoid tipo){
        return true;
    }
}
