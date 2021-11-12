package tablaDeSimbolos.nodosAST.nodosAcceso;

import java.util.ArrayList;
import java.util.List;

import analizadorLexico.Token;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.Constructor;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;
import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
import tablaDeSimbolos.tipos.Tipo;
import tablaDeSimbolos.tipos.TipoClase;

public class NodoAccesoConstructor extends NodoAccesoUnidad{
    
    protected Token tokenIdClase;
    private Constructor constructor;
    private Clase claseDelConstructor;

    public NodoAccesoConstructor(Token tokenIdClase, List<NodoExpresion> listaParametrosActuales){
        super(listaParametrosActuales);
        this.tokenIdClase = tokenIdClase;
    }

    public Tipo chequear() throws ExcepcionSemantica{
        claseDelConstructor = TablaSimbolos.getClase(tokenIdClase.getLexema());
        if(claseDelConstructor == null){
            throw new ExcepcionSemantica(tokenIdClase, "la clase "+tokenIdClase.getLexema()+" no esta declarada para su instanciacion");
        }
        //TODO: cambiar el getConstructor en caso de hacer sobrecarga etapa 4.
        constructor = claseDelConstructor.getConstructorQueConformaParametros(getListaTipos()); // Si no encuentra nada, es porque no coincidieron o en nombre, o en la lista de parametros.
        if(constructor == null){
            throw new ExcepcionSemantica(tokenIdClase, "el constructor "+tokenIdClase.getLexema()+" no esta declarado o los parametros no conforman");
        }
        
        Tipo tipoConstructor = new TipoClase(tokenIdClase);

        if(nodoEncadenado == null){
            return tipoConstructor;
        } else{
            return nodoEncadenado.chequear(tipoConstructor);
        }

    }

    private List<Tipo> getListaTipos() throws ExcepcionSemantica{
        List<Tipo> listaTiposParametrosActuales = new ArrayList<>();
        for(NodoExpresion parametroActual : listaParametrosActuales){
            listaTiposParametrosActuales.add(parametroActual.chequear());
        }
        return listaTiposParametrosActuales;
    }

    public boolean esAsignable(){
        if(nodoEncadenado != null){
            return nodoEncadenado.esAsignable();
        } else{
            return false;
        }
    }

    public boolean esLlamable(){ 
        if(nodoEncadenado != null){
            return nodoEncadenado.esLlamable();
        } else{
            return true;
        }
    }

    // Generacion de codigo intermedio

    public void generarCodigo(){
        TablaSimbolos.instruccionesMaquina.add("RMEM 1 ; Reservamos memoria para la referencia al CIR del objeto que crearemos");
        TablaSimbolos.instruccionesMaquina.add("PUSH "+ (claseDelConstructor.getCantidadAtributos() + 1) +" ; Apilamos la cant. de vars. de instancia del CIR  + 1 de la referencia a la VT para el parametro del malloc");
        TablaSimbolos.instruccionesMaquina.add("PUSH simple_malloc ; Apliamos la direccion de rutina para alojar memoria en el heap");
        TablaSimbolos.instruccionesMaquina.add("CALL");
        TablaSimbolos.instruccionesMaquina.add("DUP ; Para no perder la referencia al nuevo CIR cuando hagamos STOREREF para asociarle la VT");
        TablaSimbolos.instruccionesMaquina.add("PUSH "+claseDelConstructor.toStringLabelVT()+" ; apilamos la direccion del comienzo de la VT");
        TablaSimbolos.instruccionesMaquina.add("STOREREF 0 ; Guardamos la referencia a la VT en el CIR que creamos (siempre es en offset 0)");
        TablaSimbolos.instruccionesMaquina.add("DUP ; Duplicamos la referencia al objeto. Esta ref sera el this del RA del constructor");
        for(NodoExpresion nodoExpresion : listaParametrosActuales){
            nodoExpresion.generarCodigo();
            TablaSimbolos.instruccionesMaquina.add("SWAP ; Bajamos el this para que quede en el lugar adecuado del RA");
        }
        TablaSimbolos.instruccionesMaquina.add("PUSH "+constructor.toStringLabel()+" ; Apilamos la direccion del constructor que se determina en tiempo de compilacion");
        TablaSimbolos.instruccionesMaquina.add("CALL");
        
        if(nodoEncadenado != null){
            nodoEncadenado.generarCodigo();
        }
    }
}
