package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.tipos.Tipo;
import analizadorLexico.Token;

public abstract class NodoAccesoUnidad extends NodoPrimario{
    
    protected List<NodoExpresion> listaParametrosActuales;

    public NodoAccesoUnidad(List<NodoExpresion> listaParametrosActuales){
        this.listaParametrosActuales = listaParametrosActuales;
    }

    public static String toStringNombreUnidad(Token tokenId, List<NodoExpresion> listaParametrosActuales) throws ExcepcionSemantica{ //TODO: habria que chequear en algun momento aca que exista el Tipo del parametro actual? o eso ya se hace en pActual.chequear()?
        String signaturaUnidad = tokenId.getLexema() + "(";
        for(NodoExpresion parametroActual : listaParametrosActuales){
            Tipo tipoParametroActual = parametroActual.chequear();
            signaturaUnidad += tipoParametroActual.getNombreTipo() + ",";
        }
        if(signaturaUnidad.charAt(signaturaUnidad.length() - 1) == ','){
            signaturaUnidad = signaturaUnidad.substring(0, signaturaUnidad.length() - 1);
        }
        signaturaUnidad += ")";
        return signaturaUnidad;
    }
}
