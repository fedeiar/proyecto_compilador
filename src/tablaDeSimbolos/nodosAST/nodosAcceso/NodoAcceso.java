package tablaDeSimbolos.nodosAST.nodosAcceso;



import tablaDeSimbolos.nodosAST.nodosExpresion.NodoOperando;

public abstract class NodoAcceso extends NodoOperando{
    
    public abstract boolean esAsignable();

    public abstract boolean esLlamable();
    
}
