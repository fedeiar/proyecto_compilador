package tablaDeSimbolos;

public abstract class TipoMetodo {



    public void verificarExistenciaTipo() throws ExcepcionSemantica{ //TODO: preg si está bien
        
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return this.getClass() == tipo.getClass(); //TODO: esta muy mal? ya que es para saber si son exactamente del mismo tipo.
    }

    


    //Todos estos metodos seran redefinidos a conveniencia (devolver true)
    //TODO: preg si está bien esta forma de ver la compatibilidad.

    public abstract boolean verificarCompatibilidad(TipoMetodo tipo);


    //TODO: pero si tengo que comparar 2 Tipo, no voy a poder usar ninguno de estos metodos, ya que a ninguno de ellos puedo pasarle una variable de tipo Tipo por parametro..
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
