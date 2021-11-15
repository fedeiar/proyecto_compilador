package tablaDeSimbolos.entidades;

import tablaDeSimbolos.nodosAST.nodosExpresion.NodoExpresion;
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
    private Map<Integer, Atributo> mapeoAtributosPorOffset;
    private Map<Integer, Metodo> mapeoMetodosPorOffset;


    public Clase(Token idClase){
        this.tokenIdClase = idClase;
        constructores = new HashMap<>();
        atributos = new HashMap<>();
        metodos = new HashMap<>();

        tiposParametricos = new HashMap<>();
        listaTiposParametricos = new ArrayList<>();

        mapeoAtributosPorOffset = new HashMap<>();
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

    public Constructor getConstructorQueConformaParametros(List<Tipo> listaTiposParametrosActuales)  throws ExcepcionSemantica{
        for(Constructor constructor : constructores.values()){
            if(constructor.conformanParametros(listaTiposParametrosActuales)){
                return constructor;
            }
        }
        return null;
    }

    //TODO: AYUDA
    /*
    public Constructor getConstructorQueMasConformaParametros(String nombreConstructor, List<NodoExpresion> listaParametrosActuales) throws ExcepcionSemantica{ //TODO: asi esta bien?
        List<Tipo> listaTiposDeLosParametros = new ArrayList<>();
        for(NodoExpresion parametroActual : listaParametrosActuales){
            listaTiposDeLosParametros.add(parametroActual.chequear());
        }



    }
    */

    public Collection<Metodo> getMetodos(){
        return metodos.values();
    }

    public Metodo getMetodoQueConformaParametros(String nombreMetodo, List<Tipo> listaTiposParametrosActuales)  throws ExcepcionSemantica{
        for(Metodo metodo : metodos.values()){
            if(metodo.getTokenIdMet().getLexema().equals(nombreMetodo) && metodo.conformanParametros(listaTiposParametrosActuales)){
                return metodo;
            }
        }
        return null;
    }

    //TODO: AYUDA 
    public Metodo getMetodoQueMasConformaParametros(Token tokenIdMet, List<NodoExpresion> listaParametrosActuales)  throws ExcepcionSemantica{ //TODO: asi esta bien?
        List<Tipo> listaTiposParametrosActuales = new ArrayList<>();
        for(NodoExpresion parametroActual : listaParametrosActuales){
            listaTiposParametrosActuales.add(parametroActual.chequear());
        }

        List<Metodo> listaMetodosConformantes = new ArrayList<>();
        for(Metodo metodo : metodos.values()){
            if(metodo.conformanParametros(listaTiposParametrosActuales)){
                listaMetodosConformantes.add(metodo);
            }
        }

        if(listaMetodosConformantes.size() == 0){
            return null;
        } else if(listaMetodosConformantes.size() == 1){
            return listaMetodosConformantes.get(0);
        } else{ // Hay que decidir quien sera el mas conformante (como minimo tienen todos 1 parametro si o si)
            // Como tenemos solo metodos conformantes, entonces conformidad nunca sera -1.
            int posicionParametro = 0;
            int masConforme = Integer.MAX_VALUE;
            Metodo metodoMasConformeParaPosParametro = null;
            Metodo metodoMasConforme = null;
            // Este recorrido es equivalente a recorrer una matriz por columnas.
            while(posicionParametro < listaParametrosActuales.size()){
                for(Metodo metodo : listaMetodosConformantes){
                    Tipo tipoFormal = metodo.getParametroFormal(posicionParametro).getTipo();
                    Tipo tipoActual = listaTiposParametrosActuales.get(posicionParametro);
                    int conformidad = tipoFormal.profundidadDelHijo(tipoActual);
                    if(conformidad == masConforme){
                        metodoMasConformeParaPosParametro = null; // Si empatan, entonces aun no podemos decidir el posible metodo mas conforme.
                    }else if(conformidad < masConforme){
                        masConforme = conformidad;
                        metodoMasConformeParaPosParametro = metodo;
                    }
                }
                if(metodoMasConforme == null){ // Si estamos revisando la primer columna
                    metodoMasConforme = metodoMasConformeParaPosParametro;
                } else if(metodoMasConformeParaPosParametro != metodoMasConforme){ // Hay una ambiguedad: un metodo gana en un parametro, pero otro metodo gana en otro parametro
                    throw new ExcepcionSemantica(tokenIdMet, "la llamada al metodo "+tokenIdMet.getLexema()+" es ambigua");
                }
                posicionParametro++;
                masConforme = Integer.MAX_VALUE;
            }

            if(metodoMasConforme == null){ // Quiere decir que los mejores empataron en todos sus parametros.
                throw new ExcepcionSemantica(tokenIdMet, "la llamada al metodo "+tokenIdMet.getLexema()+" es ambigua");
            }

            return metodoMasConforme;
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
        // En esta aproximación, lo que me dice la cantidad de '#' de una variable es la ultima clase que hace uso de la version #'enesima de la variable
        /*Los '#' solamente los usamos para indicar que un atributo heredado no debe ser accesible, ya sea porque es privado o porque se duplica su nombre en la clase actual.
          Si se quiere saber si un atributo es privado o a que clase pertenece, se consulta directamente al atributo a través de su interfaz*/
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
            mapeoAtributosPorOffset.put(atributo.getOffset(), atributo);
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
        TablaSimbolos.instruccionesMaquina.add(".DATA");
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
        TablaSimbolos.instruccionesMaquina.add(etiquetasVT+"\n");

        // Codigo intermedio de los constructores y de los metodos (estaticos y dinamicos)
        TablaSimbolos.instruccionesMaquina.add(".CODE");
        String etiquetaCodigoMetodo;
        for(Metodo metodo : metodos.values()){
            if(metodo.perteneceAClase(this.tokenIdClase)){
                etiquetaCodigoMetodo = metodo.toStringLabel() + ":";
                TablaSimbolos.instruccionesMaquina.add(etiquetaCodigoMetodo);
                metodo.generarCodigo();
                TablaSimbolos.instruccionesMaquina.add(""); // Separador
            }
        }
        String etiquetaCodigoConstructor;
        for(Constructor constructor : constructores.values()){
            etiquetaCodigoConstructor = constructor.toStringLabel() + ":";
            TablaSimbolos.instruccionesMaquina.add(etiquetaCodigoConstructor);
            constructor.generarCodigo();
            TablaSimbolos.instruccionesMaquina.add(""); // Separador
        }
        TablaSimbolos.instruccionesMaquina.add("\n"); // Separador
    }

    public String toStringLabelVT(){
        return "VT_" + tokenIdClase.getLexema();
    }
}
