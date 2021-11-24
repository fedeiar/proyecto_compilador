package tablaDeSimbolos.tipos;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.TablaSimbolos;

public class TipoNull extends TipoConcreto{
    

    public TipoNull(){
    }

    public String getNombreTipo(){
        return "null";
    }

    public boolean mismoTipo(Tipo tipo){
        return tipo instanceof TipoClase;
    }

    public boolean soySubtipo(Tipo superTipo){
        return superTipo instanceof TipoClase;
    }

}
