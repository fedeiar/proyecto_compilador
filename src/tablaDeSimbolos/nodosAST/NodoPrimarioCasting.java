package tablaDeSimbolos.nodosAST;

import analizadorLexico.Token;

public class NodoPrimarioCasting extends NodoAcceso{
    
    private Token tokenIdClaseCasting;
    private NodoPrimario nodoPrimario;

    public NodoPrimarioCasting(Token tokenIdClaseCasting, NodoPrimario nodoPrimario){
        this.tokenIdClaseCasting = tokenIdClaseCasting;
        this.nodoPrimario = nodoPrimario;
    }
}
