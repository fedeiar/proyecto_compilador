package tablaDeSimbolos.tipos;


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
