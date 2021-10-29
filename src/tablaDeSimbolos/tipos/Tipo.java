package tablaDeSimbolos.tipos;
import tablaDeSimbolos.*;

public abstract class Tipo {

    public abstract String getNombreTipo();

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ // Redefinido solamente en TipoClase
        
    }

    public boolean mismoTipo(Tipo tipo){ // Si el parametro es un tipoClase, debería entrar al otro metodo.
        return this.getClass() == tipo.getClass();
    }

    public boolean mismoTipo(TipoClase tipo){ // Redefinido solamente TipoClase
        return false;
    }

    // Todos estos metodos seran redefinidos en cada Tipo concreto.

    public abstract boolean esSubtipo(Tipo tipoDelAncestro);


    public boolean visitarEsSubtipo(TipoClase tipo){
        return false;
    }
    
    public boolean visitarEsSubtipo(TipoBoolean tipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoChar tipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoInt tipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoString tipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoVoid tipo){
        return false;
    }

}
