package tablaDeSimbolos;

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

    public Clase(Token idClase){
        this.tokenIdClase = idClase;
        constructores = new HashMap<>();
        atributos = new HashMap<>();
        metodos = new HashMap<>();

        tiposParametricos = new HashMap<>();
        listaTiposParametricos = new ArrayList<>();

        estaConsolidado = false;
        estaVerificadoHerenciaCircular = false;
        if(tokenIdClase.getLexema() == "Object"){
            estaConsolidado = true; 
            estaVerificadoHerenciaCircular = true;
        }
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public Token getTokenIdClaseAncestro(){
        return tokenIdClaseAncestro;
    }

    public Atributo getAtributo(String nombreAtributo){
        return atributos.get(nombreAtributo);
    }

    public Map<String,Atributo> getHashAtributos(){
        return atributos;
    }

    public Collection<Constructor> getConstructores(){
        return constructores.values();
    }

    public Collection<Metodo> getMetodos(){
        return metodos.values();
    }

    public boolean estaConsolidado(){
        return estaConsolidado;
    }

    public void set_idClaseAncestro(Token idClaseAncestro){
        this.tokenIdClaseAncestro = idClaseAncestro;
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

    public Metodo getMetodoMismaSignatura(Metodo metodo2){ 
        Metodo metodo_en_clase = metodos.get(metodo2.toString());
        if(metodo_en_clase != null && metodo_en_clase.equalsSignatura(metodo2)){
            return metodo_en_clase;
        } else{
            return null;
        }
    }


    public void estaBienDeclarado() throws ExcepcionSemantica{
        if(!this.tokenIdClase.getLexema().equals("Object")){

            TablaSimbolos.claseActual = this; //ya que para algunos chequeos (como el constructor de Constructor) necesitamos saber la clase actual que estamos chequeando.
            if(!TablaSimbolos.getInstance().existeClase(this.tokenIdClaseAncestro.getLexema())){
                throw new ExcepcionSemantica(this.tokenIdClaseAncestro, "la clase "+this.tokenIdClaseAncestro.getLexema()+" de la cual se intenta heredar no esta declarada");
            }

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


    public void consolidar() throws ExcepcionSemantica{
        if(!estaConsolidado){
            Clase claseAncestro = TablaSimbolos.getClase(tokenIdClaseAncestro.getLexema());
            if(!claseAncestro.estaConsolidado()){
                claseAncestro.consolidar();
            }
            consolidarAtributos(claseAncestro);
            consolidarMetodos(claseAncestro);
            estaConsolidado = true;
        }
    }

    private void consolidarAtributos(Clase claseAncestro) throws ExcepcionSemantica{
        //en esta aproximación, lo que me dice la cantidad de '#' de una variable es la ultima clase que hace uso de la version #'enesima de la variable
        for(Entry<String,Atributo> atributoAncestro : claseAncestro.getHashAtributos().entrySet()){
            String nombreAtributoAncestro = atributoAncestro.getKey();
            Atributo atributo_en_clase = this.atributos.get(nombreAtributoAncestro);
            if(atributo_en_clase == null && nombreAtributoAncestro.charAt(0) != '#'){
                this.insertarAtributo(atributoAncestro.getKey(), atributoAncestro.getValue());
            } else{
                this.insertarAtributo("#"+atributoAncestro.getKey(), atributoAncestro.getValue());
            }
        }
    }

    private void consolidarMetodos(Clase claseAncestro) throws ExcepcionSemantica{
        for(Metodo metodoAncestro : claseAncestro.getMetodos()){
            Metodo metodo_en_clase = metodos.get(metodoAncestro.toString());
            if(metodo_en_clase == null){
                this.insertarMetodo(metodoAncestro); //TODO: tal vez haya que hacer un metodo insertarMetodoHeredado() y tener 2 estructuras, preguntar.
            } else{
                if(metodo_en_clase.redefineCorrectamente(metodoAncestro)){
                    //no hacer nada, ya que lo redefine
                } else{
                    throw new ExcepcionSemantica(metodo_en_clase.getTokenIdMet(), "la clase "+this.tokenIdClase.getLexema()+" redefine mal el metodo "+metodo_en_clase.getTokenIdMet().getLexema());
                }
            }
        }
    }


    public void chequearSentencias() throws ExcepcionSemantica{
        
        for(Constructor constructor : constructores.values()){
            constructor.chequearSentencias();
        }
        for(Metodo metodo: metodos.values()){
            metodo.chequearSentencias();
        }
        
    }


}
