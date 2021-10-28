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
