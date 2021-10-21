package tablaDeSimbolos.nodosAST;

public abstract class NodoAsignacion extends NodoSentencia{
    
    private NodoAcceso ladoIzq;
    


    public NodoAsignacion(NodoAcceso ladoIzq){
        this.ladoIzq = ladoIzq;
    }


}
