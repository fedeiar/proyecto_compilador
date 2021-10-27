package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoPrimario;

public class NodoPrimarioCasting extends NodoAcceso{
    
    private Token tokenIdClaseCasting;
    private NodoPrimario nodoPrimario;

    public NodoPrimarioCasting(Token tokenIdClaseCasting, NodoPrimario nodoPrimario){
        this.tokenIdClaseCasting = tokenIdClaseCasting;
        this.nodoPrimario = nodoPrimario;
    }
}
