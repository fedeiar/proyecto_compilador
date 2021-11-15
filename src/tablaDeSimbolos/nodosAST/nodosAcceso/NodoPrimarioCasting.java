package tablaDeSimbolos.nodosAST.nodosAcceso;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.tipos.*;
public class NodoPrimarioCasting extends NodoAcceso{
    
    private Token tokenIdClaseCasting;
    private NodoPrimario nodoPrimario;

    public NodoPrimarioCasting(Token tokenIdClaseCasting, NodoPrimario nodoPrimario){
        this.tokenIdClaseCasting = tokenIdClaseCasting;
        this.nodoPrimario = nodoPrimario;
    }

    public TipoConcreto chequear() throws ExcepcionSemantica{
        TipoClase tipoCasting = new TipoClase(tokenIdClaseCasting);
        tipoCasting.verificarExistenciaTipo();
        Tipo tipoNodoPrimario = nodoPrimario.chequear();
        if(! tipoCasting.esSubtipo(tipoNodoPrimario)){
            throw new ExcepcionSemantica(tokenIdClaseCasting, "casting invalido: la clase "+tokenIdClaseCasting.getLexema()+" debe ser un subtipo de la expresion a derecha");
        }

        return tipoCasting;
    }

    public boolean esAsignable(){
        return nodoPrimario.esAsignable();
    }

    public boolean esLlamable(){
        return nodoPrimario.esLlamable();
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoPrimario.establecerMismoLado(this.esLadoIzquierdoAsignacion);
        nodoPrimario.generarCodigo();
    }
}
