package tablaDeSimbolos;

public abstract class TipoMetodo {



    public void verificarExistenciaTipo() throws ExcepcionSemantica{ //TODO: preg si está bien
        
    }

    public boolean mismoTipo(TipoMetodo tipoMetodo){
        return this.getClass() == tipoMetodo.getClass(); //TODO: esta muy mal? ya que es para saber si son exactamente del mismo tipo.
    }


    //Todos estos metodos seran redefinidos a conveniencia (devolver true)

    public boolean verCompatibilidad(TipoClase tipo){
        return false;
    }

    public boolean verCompatibilidad(TipoChar tipo){
        return false;
    }

    public boolean verCompatibilidad(TipoInt tipo){
        return false;
    }

    public boolean verCompatibilidad(TipoString tipo){
        return false;
    }

    //TODO: agregar el resto de los tipos.

}
