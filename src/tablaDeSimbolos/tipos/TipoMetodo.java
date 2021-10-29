package tablaDeSimbolos.tipos;
import tablaDeSimbolos.*;

public abstract class TipoMetodo {


    public abstract String getNombreTipo();

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ // Redefinido solamente en TipoClase
        
    }

    public abstract boolean mismoTipo(TipoMetodo tipo); //TODO: considerar tener 1 solo  metodo mismoTipo, sin el visitar.

    public boolean visitarMismoTipo(TipoMetodo tipo){
        return this.getClass() == tipo.getClass();
    }

    public boolean visitarMismoTipo(TipoClase tipo){ // Redefinido solamente en TipoClase
        return false;                          
    }


    // Todos estos metodos seran redefinidos en cada Tipo concreto.

    public abstract boolean esSubtipo(TipoMetodo tipoDelAncestro);


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
