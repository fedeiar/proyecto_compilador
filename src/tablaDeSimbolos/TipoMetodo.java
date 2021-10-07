package tablaDeSimbolos;

public abstract class TipoMetodo {


    //Todos estos metodos seran redefinidos a conveniencia (devolver true)

    //TODO: es posible tener estos metodos en Tipo en lugar de TipoMetodo, y que solamente tengamos en TipoMetodo para ver si es void o ei es un Tipo?

    public boolean verCompatibilidad(TipoClase tipo){
        return false;
    }

    public boolean verCompatibilidad(TipoChar tipo){
        return false;
    }

    public boolean verCompatibilidad(TipoInt tipo){
        return false;
    }

    //TODO: agregar el resto de los tipos.

}
