package tablaDeSimbolos.nodosAST;

public abstract class NodoPrimario extends NodoAcceso{
    
    protected NodoEncadenado nodoEncadenado;


    public void insertarNodoEncadenado(NodoEncadenado nodoEncadenado){
        this.nodoEncadenado = nodoEncadenado;
    }
}
