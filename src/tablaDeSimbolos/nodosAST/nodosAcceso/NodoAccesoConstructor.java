package tablaDeSimbolos.nodosAST.nodosAcceso;

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
        constructor = claseDelConstructor.getConstructorQueConformaParametros(NodoAccesoUnidad.getListaTipos(listaParametrosActuales)); // Si no encuentra nada, es porque no coincidieron o en nombre, o en la lista de parametros.
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
        TablaSimbolos.listaInstruccionesMaquina.add("RMEM 1 ; Reservamos memoria para el resultado del malloc (la referencia al nuevo CIR del objeto que crearemos)");
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+ (claseDelConstructor.getCantidadAtributos() + 1) +" ; Apilamos la cant. de vars. de instancia del CIR  + 1 de la referencia a la VT que sera el parametro del malloc");
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH simple_malloc ; Apliamos la direccion de rutina para alojar memoria en el heap");
        TablaSimbolos.listaInstruccionesMaquina.add("CALL");
        TablaSimbolos.listaInstruccionesMaquina.add("DUP ; Para no perder la referencia al nuevo CIR cuando hagamos STOREREF para asociarle la VT");
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+ claseDelConstructor.toStringLabelVT() +" ; apilamos la direccion del comienzo de la VT");
        TablaSimbolos.listaInstruccionesMaquina.add("STOREREF 0 ; Guardamos la referencia a la VT en el CIR que creamos (siempre es en offset 0)");
        TablaSimbolos.listaInstruccionesMaquina.add("DUP ; Duplicamos la referencia al objeto porque sera tanto el resultado del constructor como la referencia a this dentro de la ejecucion del constructor");
        for(NodoExpresion nodoExpresion : listaParametrosActuales){
            nodoExpresion.generarCodigo();
            TablaSimbolos.listaInstruccionesMaquina.add("SWAP ; Bajamos el this para que quede en el lugar adecuado del RA");
        }
        TablaSimbolos.listaInstruccionesMaquina.add("PUSH "+constructor.toStringLabel()+" ; Apilamos la direccion del constructor que se determina en tiempo de compilacion");
        TablaSimbolos.listaInstruccionesMaquina.add("CALL");
        
        if(nodoEncadenado != null){
            nodoEncadenado.establecerMismoLado(this.esLadoIzquierdoAsignacion);
            nodoEncadenado.generarCodigo();
        }
    }
}
