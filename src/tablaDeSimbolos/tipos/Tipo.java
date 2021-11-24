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

    /* Devuelve true si algun tipo es subtipo del otro, false caso contrario */
    public boolean estanRelacionados(Tipo tipo){
        return this.soySubtipo(tipo) || tipo.soySubtipo(this);
    }

    // Calcula la conformidad de la lista de parametros.

    /* Devuelve -1 si no es un TipoClase, o n si es un TipoClase, donde n es la distancia del tipoClase hasta Object. */ 
    public int nivelDeProfundidad(){ // Redefinido solamente en TipoClase
        return -1;
    }

    
}
