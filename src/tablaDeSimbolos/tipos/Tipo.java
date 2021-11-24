package tablaDeSimbolos.tipos;
import tablaDeSimbolos.entidades.ExcepcionSemantica;

public abstract class Tipo {

    public abstract String getNombreTipo();

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ // Redefinido solamente en TipoClase
    }

    public boolean mismoTipo(Tipo tipo){ // Redefinido solamente en TipoClase y TipoNull
        return this.getClass() == tipo.getClass();
    }

    // Verifica relacion de subtipo entre tipos

    public boolean soySubtipo(Tipo superTipo){ // Redefinido solamente en TipoClase y TipoNull
        return this.getClass() == superTipo.getClass();
    }

    // Este metodo es para calcular la conformidad de la lista de parametros

    /* Devuelve -1 si no estan relacionados, 0 si son el mismo tipo, y n donde n es la cantidad de niveles (cantidad de arcos) de herencia que separan al ancestro del hijo */ 
    public int distanciaPadre(Tipo superTipo){ // Redefinido solamente TipoClase y TipoNull
        if(this.getClass() == superTipo.getClass()){
            return 0;
        } else{
            return -1;
        }
    }
}
