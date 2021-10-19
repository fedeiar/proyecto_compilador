package tablaDeSimbolos.tipos;
import tablaDeSimbolos.*;

public abstract class TipoMetodo {


    public abstract String getNombreTipo();

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ 
        
    }

    public abstract boolean mismoTipo(TipoMetodo tipo);

    public boolean visitarMismoTipo(TipoMetodo tipo){
        return this.getClass() == tipo.getClass();
    }

    public boolean visitarMismoTipo(TipoClase tipo){ //redefinido solamente en TipoClase
        return false;                          
    }


    //Todos estos metodos seran redefinidos a conveniencia (devolver true)

    public abstract boolean verificarCompatibilidad(TipoMetodo tipo);


    public boolean VisitarVerCompatibilidad(TipoClase tipo){
        return false;
    }
    
    public boolean VisitarVerCompatibilidad(TipoBoolean tipo){
        return false;
    }

    public boolean VisitarVerCompatibilidad(TipoChar tipo){
        return false;
    }

    public boolean VisitarVerCompatibilidad(TipoInt tipo){
        return false;
    }

    public boolean VisitarVerCompatibilidad(TipoString tipo){
        return false;
    }

    public boolean VisitarVerCompatibilidad(TipoVoid tipo){
        return false;
    }

}
