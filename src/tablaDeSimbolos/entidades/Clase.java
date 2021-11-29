package tablaDeSimbolos.entidades;

import tablaDeSimbolos.tipos.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Clase {
    
    private Token tokenIdClase;
    private Token tokenIdClaseAncestro;

    private Map<String, Constructor> constructores;
    private Map<String, Atributo> atributos;
    private Map<String, Metodo> metodos;

    private List<TipoClase> listaTiposParametricos;
    private Map<String, TipoClase> tiposParametricos;
    
    private boolean estaConsolidado;
    private boolean estaVerificadoHerenciaCircular;

    private int offsetDisponibleCIR; // El primer número de offset disponible en el CIR
    private int offsetDisponibleVT; // El primer número de offset disponible en la VT
    private Map<Integer, Metodo> mapeoMetodosPorOffset;


    public Clase(Token idClase){
        this.tokenIdClase = idClase;
        constructores = new HashMap<>();
        atributos = new HashMap<>();
        metodos = new HashMap<>();

        tiposParametricos = new HashMap<>();
        listaTiposParametricos = new ArrayList<>();

        mapeoMetodosPorOffset = new HashMap<>();

        estaConsolidado = false;
        estaVerificadoHerenciaCircular = false;

        if(tokenIdClase.getLexema().equals("Object")){
            estaConsolidado = true; 
            estaVerificadoHerenciaCircular = true;
            offsetDisponibleCIR = 1; // Ya que el 0 lo ocupa la VT
            offsetDisponibleVT = 0; // Cuando se agreguen metodos dinamicos en las clases hijas de Object se incrementara en esas clases.
        }
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public Token getTokenIdClaseAncestro(){
        return tokenIdClaseAncestro;
    }

    public void set_idClaseAncestro(Token idClaseAncestro){
        this.tokenIdClaseAncestro = idClaseAncestro;
    }

    public Atributo getAtributo(String nombreAtributo){
        return atributos.get(nombreAtributo);
    }

    public Map<String,Atributo> getHashAtributos(){
        return atributos;
    }

    public int getCantidadAtributos(){
        return atributos.size();
    }

    public Constructor getConstructorQueMasConformaParametros(Token tokenIdClase, List<Tipo> listaTiposParametrosActuales) throws ExcepcionSemantica{
        List<Constructor> listaConstructoresConformantes = new ArrayList<>();
        for(Constructor constructor : constructores.values()){
            if(constructor.conformanParametros(listaTiposParametrosActuales)){
                listaConstructoresConformantes.add(constructor);
            }
        }

        if(listaConstructoresConformantes.size() == 0){
            return null;
        } else if(listaConstructoresConformantes.size() == 1){
            return listaConstructoresConformantes.get(0);
        } else{ // Hay que decidir quien sera el mas conformante (todos tienen al menos 1 parametro si o si, y por lo menos en una posición son TipoClase)
            // Como tenemos solo constructores conformantes (es decir, todos los parametros actuales conforman con los formales), solo nos interesa la profundidad del parametro formal.
            int posicionParametro = 0;
            int masProfundo;
            List<Constructor> listaConstructoresGanadoresParaPos = new ArrayList<>();
            List<Constructor> listaConstructoresGanadores = new ArrayList<>();
            for(Constructor constructor : listaConstructoresConformantes){ // Inicialmente, todos comienzan en la lista de ganadores, después van a ir siendo derrotados hasta que quede 1 solo.
                listaConstructoresGanadores.add(constructor);
            }
            // Este recorrido es equivalente a recorrer una matriz por columnas.
            // Descartamos constructores de la lista de ganadores en base a los parametros de las otras posiciones, para quedarnos con el que mas conforma.
            while(posicionParametro < listaTiposParametrosActuales.size()){
                masProfundo = -1;
                for(Constructor constructor : listaConstructoresConformantes){
                    Tipo tipoFormal = constructor.getParametroFormal(posicionParametro).getTipo();
                    int profundidad = tipoFormal.nivelDeProfundidad();
                    if(profundidad > masProfundo){ // Hay una victoria en esa posicion, es un posible ganador, hay que agregarlo a la lista
                        masProfundo = profundidad;
                        listaConstructoresGanadoresParaPos.clear(); // Derroto a todos los demas, asi que hay que borrarlos
                        listaConstructoresGanadoresParaPos.add(constructor);
                    } else if(profundidad == masProfundo){ // Hay un empate en esa posicion, es un posible ganador, hay que agregarlo a la lista
                        listaConstructoresGanadoresParaPos.add(constructor);
                    } else{ // El metodo fue derrotado, no lo agregamos a la lista
                    }
                }
                // Hay que quitar de la listaConstructoresGanadores a los constructores que no quedaron en los ganadores de esa posicion.
                List<Constructor> listaAuxiliarGanadores = new ArrayList<>();
                for(Constructor constructor : listaConstructoresGanadores){
                    if(listaConstructoresGanadoresParaPos.contains(constructor)){
                        listaAuxiliarGanadores.add(constructor);
                    }
                }
                listaConstructoresGanadoresParaPos.clear();
                listaConstructoresGanadores = listaAuxiliarGanadores;
                posicionParametro++;
            }
            if(listaConstructoresGanadores.size() != 1){ // Significa que no hubo un ganador, error de ambiguedad.
                throw new ExcepcionSemantica(tokenIdClase, "la llamada al metodo "+tokenIdClase.getLexema()+" es ambigua");
            }
            return listaConstructoresGanadores.get(0);
        }
    }

    public Collection<Metodo> getMetodos(){
        return metodos.values();
    }

    // Faltaria chequear que en caso de que el tipoActual de la pos i-esima sea null, entonces todos los tiposFormales de la pos i-esima esten relacionados en por herencia, sino error.
    public Metodo getMetodoQueMasConformaParametros(Token tokenIdMet, List<Tipo> listaTiposParametrosActuales) throws ExcepcionSemantica{
        List<Metodo> listaMetodosConformantes = new ArrayList<>();
        for(Metodo metodo : metodos.values()){
            if(metodo.getTokenIdMet().getLexema().equals(tokenIdMet.getLexema()) && metodo.conformanParametros(listaTiposParametrosActuales)){
                listaMetodosConformantes.add(metodo);
            }
        }

        if(listaMetodosConformantes.size() == 0){
            return null;
        } else if(listaMetodosConformantes.size() == 1){
            return listaMetodosConformantes.get(0);
        } else{ // Hay que decidir quien sera el mas conformante (todos tienen al menos 1 parametro si o si, y por lo menos en una posición son TipoClase)
            // Como tenemos solo metodos conformantes (es decir, todos los parametros actuales conforman con los formales), solo nos interesa la profundidad del parametro formal.
            int posicionParametro = 0;
            int masProfundo;
            List<Metodo> listaMetodosGanadoresParaPos = new ArrayList<>();
            List<Metodo> listaMetodosGanadores = new ArrayList<>();
            for(Metodo metodo : listaMetodosConformantes){ // Inicialmente, todos comienzan en la lista de ganadores, después van a ir siendo derrotados hasta que quede 1 solo.
                listaMetodosGanadores.add(metodo);
            }
            // Este recorrido es equivalente a recorrer una matriz por columnas.
            // Descartamos metodos de la lista de ganadores en base a los parametros de las otras posiciones, para quedarnos con el que mas conforma.
            while(posicionParametro < listaTiposParametrosActuales.size()){
                masProfundo = -1;
                for(Metodo metodo : listaMetodosConformantes){
                    Tipo tipoFormal = metodo.getParametroFormal(posicionParametro).getTipo();
                    int profundidad = tipoFormal.nivelDeProfundidad();
                    if(profundidad > masProfundo){ // Hay una victoria en esa posicion, es un posible ganador, hay que agregarlo a la lista
                        masProfundo = profundidad;
                        listaMetodosGanadoresParaPos.clear(); // Derroto a todos los demas, asi que hay que borrarlos
                        listaMetodosGanadoresParaPos.add(metodo);
                    } else if(profundidad == masProfundo){ // Hay un empate en esa posicion, es un posible ganador, hay que agregarlo a la lista
                        listaMetodosGanadoresParaPos.add(metodo);
                    } else{ // El metodo fue derrotado, no lo agregamos a la lista
                    }
                }
                // Hay que quitar de la listaMetodosGanadores a los metodos que no quedaron en los ganadores de esa posicion.
                List<Metodo> listaAuxiliarGanadores = new ArrayList<>();
                for(Metodo metodo : listaMetodosGanadores){
                    if(listaMetodosGanadoresParaPos.contains(metodo)){
                        listaAuxiliarGanadores.add(metodo);
                    }
                }
                listaMetodosGanadoresParaPos.clear();
                listaMetodosGanadores = listaAuxiliarGanadores;
                posicionParametro++;
            }
            if(listaMetodosGanadores.size() != 1){ // Significa que no hubo un ganador, error de ambiguedad.
                throw new ExcepcionSemantica(tokenIdMet, "la llamada al metodo "+tokenIdMet.getLexema()+" es ambigua");
            }
            return listaMetodosGanadores.get(0);
        }
    }
    

    public Metodo getMetodoMismaSignatura(Metodo metodo2){ 
        Metodo metodo_en_clase = metodos.get(metodo2.toString());
        if(metodo_en_clase != null && metodo_en_clase.equalsSignatura(metodo2)){
            return metodo_en_clase;
        } else{
            return null;
        }
    }

    public void insertarTipoParametrico(String nombreTipoParametrico, TipoClase tipoParametrico) throws ExcepcionSemantica{ //TODO: usar para logro genericidad.
        if(tiposParametricos.get(nombreTipoParametrico) == null){
            tiposParametricos.put(nombreTipoParametrico, tipoParametrico);
            listaTiposParametricos.add(tipoParametrico);
        } else{
            throw new ExcepcionSemantica(tipoParametrico.getTokenIdClase(), "ya existe un tipo parametrico con el mismo nombre que "+tipoParametrico.getTokenIdClase().getLexema());
        }
    }

    public void insertarAtributo(String nombreAtributo, Atributo atributo) throws ExcepcionSemantica{
        if(atributos.get(nombreAtributo) == null){
            atributos.put(nombreAtributo, atributo);
        } else{
            throw new ExcepcionSemantica(atributo.getTokenIdVar(), "el atributo "+ nombreAtributo +" ya esta declarado dentro de la clase "+tokenIdClase.getLexema());
        }
        
    }
    
    public void insertarConstructor(Constructor constructor_a_insertar) throws ExcepcionSemantica{
        Constructor constructor_en_clase = constructores.get(constructor_a_insertar.toString());
        if(constructor_en_clase == null){
            constructores.put(constructor_a_insertar.toString(), constructor_a_insertar);
        } else{
            throw new ExcepcionSemantica(constructor_a_insertar.getTokenIdClase(), "ya existe otro constructor con los mismos parametros dentro la clase "+tokenIdClase.getLexema()); 
        }
    }

    public void insertarMetodo(Metodo metodo_a_insertar) throws ExcepcionSemantica{  
        if(metodos.get(metodo_a_insertar.toString()) == null){
            metodos.put(metodo_a_insertar.toString(), metodo_a_insertar);
        } else{
            throw new ExcepcionSemantica(metodo_a_insertar.getTokenIdMet(), "existe otro metodo con el mismo nombre y parametros dentro de la clase "+tokenIdClase.getLexema());
        }
        
    }

    public boolean existeTipoParametrico(Token tokenIdClase){
        if(tiposParametricos.get(tokenIdClase.getLexema()) != null){
            return true;
        } else{
            return false;
        }
    }

    public int getOffsetDisponibleEnCIR(){
        return offsetDisponibleCIR;
    }

    public int getOffsetDisponibleEnVT(){
        return offsetDisponibleVT;
    }

    
    // -----Chequeos semanticos-----

    // Chequeo de declaraciones

    public void estaBienDeclarado() throws ExcepcionSemantica{
        TablaSimbolos.claseActual = this; // Ya que para algunos chequeos (como el constructor de Constructor) necesitamos saber la clase actual que estamos chequeando.

        verificarExistenciaAncestro();

        verificarHerenciaCircular(new HashMap<String, Clase>());

        for(Atributo a : atributos.values()){
            a.estaBienDeclarado();
        }

        for(Metodo metodo : metodos.values()){
            metodo.estaBienDeclarado();
        }

        for(Constructor c : constructores.values()){
            c.estaBienDeclarado();
        }

        if(constructores.size() == 0){
            insertarConstructor(new Constructor(new Token(TipoDeToken.id_clase, this.tokenIdClase.getLexema(), 0)));
        } 
    }

    private void verificarExistenciaAncestro() throws ExcepcionSemantica{
        if(!this.tokenIdClase.getLexema().equals("Object") && !TablaSimbolos.getInstance().existeClase(this.tokenIdClaseAncestro.getLexema())){
            throw new ExcepcionSemantica(this.tokenIdClaseAncestro, "la clase "+this.tokenIdClaseAncestro.getLexema()+" de la cual se intenta heredar no esta declarada");
        }
    }

    public void verificarHerenciaCircular(Map<String, Clase> clases_ancestro) throws ExcepcionSemantica{
        if(!estaVerificadoHerenciaCircular){ 
            if(clases_ancestro.get(this.tokenIdClaseAncestro.getLexema()) != null){
                throw new ExcepcionSemantica(this.tokenIdClaseAncestro, "la clase "+this.tokenIdClase.getLexema()+" hereda circularmente de "+ this.tokenIdClaseAncestro.getLexema());
            } else{
                clases_ancestro.put(this.tokenIdClase.getLexema(), this);
                Clase clase_ancestra = TablaSimbolos.getClase(tokenIdClaseAncestro.getLexema());
                clase_ancestra.verificarHerenciaCircular(clases_ancestro);
            }
            estaVerificadoHerenciaCircular = true;
        }
    }

    public boolean estaConsolidado(){
        return estaConsolidado;
    }

    public void consolidar() throws ExcepcionSemantica{
        if(!estaConsolidado){
            Clase claseAncestro = TablaSimbolos.getClase(tokenIdClaseAncestro.getLexema());
            if(!claseAncestro.estaConsolidado()){
                claseAncestro.consolidar();
            }
            consolidarAtributos(claseAncestro);
            offsetAtributos(claseAncestro);
            consolidarMetodos(claseAncestro);
            offsetMetodos(claseAncestro);
            estaConsolidado = true;
        }
    }

    private void consolidarAtributos(Clase claseAncestro) throws ExcepcionSemantica{
        /* Los '#' solamente los usamos para indicar que un atributo heredado no debe ser accesible, ya sea porque es privado o porque se duplica su nombre en la clase actual.
          Si se quiere saber si un atributo es privado o a que clase pertenece, se consulta directamente al atributo a través de su interfaz */
        for(Entry<String,Atributo> entryAtributoAncestro : claseAncestro.getHashAtributos().entrySet()){
            String nombreAtributoAncestro = entryAtributoAncestro.getKey();
            Atributo atributoAncestro = entryAtributoAncestro.getValue();
            Atributo atributo_en_clase = this.atributos.get(nombreAtributoAncestro);

            if(atributo_en_clase == null && nombreAtributoAncestro.charAt(0) != '#'){
                if(entryAtributoAncestro.getValue().esPublic())
                    this.insertarAtributo(nombreAtributoAncestro, atributoAncestro);
                else
                    this.insertarAtributo("#"+nombreAtributoAncestro, atributoAncestro);
            } else{
                this.insertarAtributo("#"+nombreAtributoAncestro, atributoAncestro);
            }
        }
    }

    private void offsetAtributos(Clase claseAncestro){
        this.offsetDisponibleCIR = claseAncestro.getOffsetDisponibleEnCIR();
        for(Atributo atributo : atributos.values()){
            if(!atributo.tieneOffsetAsignado()){ // Si no tiene offset asignado, significa que es un atributo declarado en esta clase y debemos ponerle un offset.
                this.agregarOffsetAtributoEnCIR(atributo);
            }
        }
    }

    private void agregarOffsetAtributoEnCIR(Atributo atributo){
        atributo.setOffset(offsetDisponibleCIR);
        offsetDisponibleCIR++;
    }

    private void consolidarMetodos(Clase claseAncestro) throws ExcepcionSemantica{
        for(Metodo metodoAncestro : claseAncestro.getMetodos()){
            Metodo metodo_en_clase = metodos.get(metodoAncestro.toString());
            if(metodo_en_clase == null){
                this.insertarMetodo(metodoAncestro);
            } else{
                if(metodo_en_clase.redefineCorrectamente(metodoAncestro)){
                    metodo_en_clase.setOffset(metodoAncestro.getOffset()); // Ya que los metodos redefinidos deben tener el mismo offset que en el padre.
                } else{
                    throw new ExcepcionSemantica(metodo_en_clase.getTokenIdMet(), "la clase "+this.tokenIdClase.getLexema()+" redefine mal el metodo "+metodo_en_clase.getTokenIdMet().getLexema());
                }
            }
        }
    }

    private void offsetMetodos(Clase claseAncestro){  
        this.offsetDisponibleVT = claseAncestro.getOffsetDisponibleEnVT();  
        for(Metodo metodo : metodos.values()){
            if(metodo.esDinamico()){ // Los metodos estaticos no van a estar en la VT asi que no se les asigna un offset
                if(!metodo.tieneOffsetAsignado()){ // De esta forma no tocamos a los redefinidos ya que les pusimos offset en el for anterior, ni a los que no fueron declarados en esta clase.
                    this.agregarOffsetMetodoEnVT(metodo);
                }
                mapeoMetodosPorOffset.put(metodo.getOffset(), metodo);
            }
        }
    }

    private void agregarOffsetMetodoEnVT(Metodo metodo){
        metodo.setOffset(offsetDisponibleVT);
        offsetDisponibleVT++;
    }


    // Chequeo de sentencias

    public void chequearSentencias() throws ExcepcionSemantica{
        TablaSimbolos.claseActual = this;
        for(Constructor constructor : constructores.values()){
            constructor.chequearSentencias();
        }
        for(Metodo metodo: metodos.values()){
            if(metodo.perteneceAClase(this.tokenIdClase)){
                metodo.chequearSentencias();
            }
        }
    }


    // Generación de codigo intermedio

    public void generarCodigo(){
        TablaSimbolos.claseActual = this;
        // Creacion de la VT. Si no tiene métodos dinámicos, entonces hay que hacer una VT que tenga una etiqueta vacía (NOP)
        TablaSimbolos.listaInstruccionesMaquina.add(".DATA");
        String etiquetasVT;
        if(mapeoMetodosPorOffset.size() != 0){
            etiquetasVT = this.toStringLabelVT() + ": DW ";
            for(int offset = 0; offset < mapeoMetodosPorOffset.size(); offset++){
                Metodo metodo = mapeoMetodosPorOffset.get(offset);
                etiquetasVT += metodo.toStringLabel() + ",";
            }
            etiquetasVT = etiquetasVT.substring(0, etiquetasVT.length() - 1); // Sacamos la ultima ","
        } else{
            etiquetasVT = this.toStringLabelVT() + ": NOP";
        }
        TablaSimbolos.listaInstruccionesMaquina.add(etiquetasVT+"\n");

        // Codigo intermedio de los constructores y de los metodos (estaticos y dinamicos)
        TablaSimbolos.listaInstruccionesMaquina.add(".CODE");
        String etiquetaCodigoMetodo;
        for(Metodo metodo : metodos.values()){
            if(metodo.perteneceAClase(this.tokenIdClase)){
                etiquetaCodigoMetodo = metodo.toStringLabel() + ":";
                TablaSimbolos.listaInstruccionesMaquina.add(etiquetaCodigoMetodo);
                metodo.generarCodigo();
                TablaSimbolos.listaInstruccionesMaquina.add(""); // Separador
            }
        }
        String etiquetaCodigoConstructor;
        for(Constructor constructor : constructores.values()){
            etiquetaCodigoConstructor = constructor.toStringLabel() + ":";
            TablaSimbolos.listaInstruccionesMaquina.add(etiquetaCodigoConstructor);
            constructor.generarCodigo();
            TablaSimbolos.listaInstruccionesMaquina.add(""); // Separador
        }
        TablaSimbolos.listaInstruccionesMaquina.add("\n"); // Separador
    }

    public String toStringLabelVT(){
        return "VT_" + tokenIdClase.getLexema();
    }
}
