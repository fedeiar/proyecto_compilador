package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoVoid;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.Constructor;

public class NodoAccesoSuperConstructor extends NodoAcceso{
    
    private Token tokenSuper;
    private List<NodoExpresion> listaParametrosActuales;
    
    private Constructor constructor;


    public NodoAccesoSuperConstructor(Token tokenSuper, List<NodoExpresion> listaParametrosActuales){
        this.tokenSuper = tokenSuper;
        this.listaParametrosActuales = listaParametrosActuales;
    }

    public Tipo chequear() throws ExcepcionSemantica{
        if( !(TablaSimbolos.unidadActual instanceof Constructor) ){
            throw new ExcepcionSemantica(tokenSuper, "solo se puede llamar al constructor del padre con super dentro de un constructor");
        }
        
        Token tokenClasePadre = TablaSimbolos.claseActual.getTokenIdClaseAncestro();
        Clase clasePadre = TablaSimbolos.getClase(tokenClasePadre.getLexema());
        constructor = clasePadre.getConstructorQueMasConformaParametros(tokenClasePadre, NodoAccesoUnidad.getListaTipos(listaParametrosActuales));
        if(constructor == null){
            throw new ExcepcionSemantica(tokenSuper, "los parametros para super() no conforman con ninguno de los constructores de la clase");
        }

        Tipo tipoConstructorSuper = new TipoVoid();

        return tipoConstructorSuper;
    }

    public boolean esAsignable(){
        return false;
    }

    public boolean esLlamable(){
        return true;
    }

    public void generarCodigo(){
        TablaSimbolos.listaInstruccionesMaquina.add("LOAD 3 ; Apilo this en la pila");
        for(NodoExpresion nodoExpresion : listaParametrosActuales){
            nodoExpresion.generarCodigo();
            TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Bajamos el this para que quede en el lugar adecuado del RA");
        }
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+ constructor.toStringLabel() +" ; Apilamos la direccion del constructor a invocar, que se determina en tiempo de compilacion");
        TablaSimbolos.listaInstruccionesMaquina.add("CALL");
    }


}
