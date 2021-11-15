package tablaDeSimbolos.nodosAST.nodosSentencia;

import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosAcceso.NodoAcceso;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoVoid;
import analizadorLexico.Token;

public class NodoLlamada extends NodoSentencia{
    
    private Token tokenPuntoYComa;
    private NodoAcceso nodoAcceso;
    
    private Tipo tipoLlamada;

    public NodoLlamada(Token tokenPuntoYComa, NodoAcceso nodoAcceso){
        this.nodoAcceso = nodoAcceso;
        this.tokenPuntoYComa = tokenPuntoYComa;
    }

    public void chequear() throws ExcepcionSemantica{
        tipoLlamada = nodoAcceso.chequear();

        if( !nodoAcceso.esLlamable()){
            throw new ExcepcionSemantica(tokenPuntoYComa, "se esperaba una llamada a metodo o constructor");
        }
    }


    // Generacion de codigo intermedio

    public void generarCodigo(){
        nodoAcceso.generarCodigo();
        if(!tipoLlamada.mismoTipo(new TipoVoid())){
            TablaSimbolos.instruccionesMaquina.add("POP ; La llamada devolvio un valor que no fue asignado en ninguna variable, debe descartarse para no afectar el .STACK"); 
        }
    }
}
