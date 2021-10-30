package tablaDeSimbolos.tipos;
import tablaDeSimbolos.entidades.ExcepcionSemantica;

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


    public boolean visitarEsSubtipo(TipoClase subtipo){
        return false;
    }
    
    public boolean visitarEsSubtipo(TipoBoolean subtipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoChar subtipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoInt subtipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoString subtipo){
        return false;
    }

    public boolean visitarEsSubtipo(TipoVoid subtipo){
        return false;
    }


    // Este metodo es para calcular la conformidad de la lista de parametros

    /* Devuelve -1 si no estan relacionados, 0 si son el mismo tipo, y n donde n es la cantidad de niveles (cantidad de arcos) de herencia que separan al ancestro del hijo*/ 
    public int profundidadDelHijo(Tipo subtipo){ //TODO: preg si esta bien.
        if(this.getClass() == subtipo.getClass()){
            return 0;
        }else{
            return -1;
        }
    }
    //TODO: preg si esta bien.
    public int profundidadDelHijo(TipoClase subtipo){ // Redefinido solamente TipoClase y TipoNull
        return -1;
    }

}
