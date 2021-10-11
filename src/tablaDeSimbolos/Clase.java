package tablaDeSimbolos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Clase {
    

    private Token tokenIdClase;
    private Token tokenIdClaseAncestro;
    private Constructor constructor;
    private Map<String, Atributo> atributos;
    private Map<String, Metodo> metodos;
    
    private boolean estaConsolidado;
    private boolean estaVerificadoHerenciaCircular; //TODO: usarlo para verificarHerenciaCircular

    public Clase(Token idClase){
        this.tokenIdClase = idClase;
        atributos = new HashMap<>();
        metodos = new HashMap<>();

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

    public Collection<Atributo> getAtributos(){
        return atributos.values();
    }

    public Metodo getMetodo(String nombreMetodo){
        return metodos.get(nombreMetodo);
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

    public void insertarAtributo(String nombreAtributo, Atributo atributo) throws ExcepcionSemantica{
        if(atributos.get(nombreAtributo) == null){
            atributos.put(nombreAtributo, atributo);
        } else{
            throw new ExcepcionSemantica(atributo.getTokenIdVar(), "ya existe un atributo con el mismo nombre dentro de esta clase");
        }
        
    }
    
    public void insertarConstructor(Constructor constructor) throws ExcepcionSemantica{
        if(this.constructor == null){
            //TODO: para el logro va a haber que modificarlo y usar un hashMap como con metodos y atributos.
            this.constructor = constructor;
        } else{
            throw new ExcepcionSemantica(constructor.getTokenIdClase(), ""); //TODO: modificar para sobrecarga.
        }
    }

    public void insertarMetodo(String nombreMetodo, Metodo metodo) throws ExcepcionSemantica{
        if(metodos.get(nombreMetodo) == null){
            metodos.put(nombreMetodo, metodo);
        } else{
            throw new ExcepcionSemantica(metodo.getTokenIdMet(), "existe otro metodo con el mismo nombre en esta clase");
        }
    }

    public boolean existeMetodo(Metodo metodo2){
        Metodo metodoEnClase = metodos.get(metodo2.getTokenIdMet().getLexema());
        if(metodoEnClase != null && metodoEnClase.equalsSignatura(metodo2)){
            return true;
        } else{
            return false;
        }
    }




    public void estaBienDeclarado() throws ExcepcionSemantica{
        if(!this.tokenIdClase.getLexema().equals("Object")){ //TODO: hay una forma mejor que hacer esto?

            TablaSimbolos.claseActual = this; //ya que para algunos chequeos (como el constructor de Constructor) necesitamos saber la clase actual que estamos chequeando.
            if(!TablaSimbolos.getInstance().existeClase(this.tokenIdClaseAncestro.getLexema())){
                throw new ExcepcionSemantica(this.tokenIdClaseAncestro, "la clase "+this.tokenIdClaseAncestro.getLexema()+" de la cual se intenta heredar no esta declarada");
            }

            verificarHerenciaCircular(new HashMap<String, Clase>());

            for(Atributo a : atributos.values()){
                a.estaBienDeclarado();
            }

            for(Metodo m : metodos.values()){
                m.estaBienDeclarado();
            }

            if(this.constructor == null){
                constructor = new Constructor(new Token(TipoDeToken.id_clase, this.tokenIdClase.getLexema(), 0)); //TODO: está bien creado el constructor por default?
            }

            constructor.estaBienDeclarado();

            //TODO: verificar otras cosas
        }

    }

    public void verificarHerenciaCircular(Map<String, Clase> clases_ancestro) throws ExcepcionSemantica{
        if(!estaVerificadoHerenciaCircular){ //TODO: está bien cambiada la condición (this.tokenIdClaseAncestro != null) por la actual? sigue siendo correcto?
            if(clases_ancestro.get(this.tokenIdClaseAncestro.getLexema()) != null){
                throw new ExcepcionSemantica(this.tokenIdClaseAncestro, "la clase "+this.tokenIdClase.getLexema()+" hereda circularmente de "+ this.tokenIdClaseAncestro.getLexema());
            } else{
                clases_ancestro.put(this.tokenIdClase.getLexema(), this);
                Clase clase_ancestra = TablaSimbolos.getInstance().getClase(tokenIdClaseAncestro.getLexema());
                clase_ancestra.verificarHerenciaCircular(clases_ancestro);
            }
            estaVerificadoHerenciaCircular = true; //TODO: preg si esta bien setearlo en true aca
        }
    }


    public void consolidar() throws ExcepcionSemantica{
        if(!estaConsolidado){
            Clase claseAncestro = TablaSimbolos.getInstance().getClase(tokenIdClaseAncestro.getLexema());
            if(!claseAncestro.estaConsolidado()){
                claseAncestro.consolidar();
            }
            consolidarAtributos(claseAncestro);
            consolidarMetodos(claseAncestro);
            estaConsolidado = true;
        }
    }

    private void consolidarAtributos(Clase claseAncestro) throws ExcepcionSemantica{ 
        for(Atributo atributo : claseAncestro.getAtributos()){
            Atributo atributo_en_clase = atributos.get(atributo.getTokenIdVar().getLexema());
            if(atributo_en_clase == null){
                this.insertarAtributo(atributo.getTokenIdVar().getLexema(), atributo);
            }
            else{
                throw new ExcepcionSemantica(atributo_en_clase.getTokenIdVar(), "ya existe un atributo con el mismo nombre que "+atributo_en_clase.getTokenIdVar().getLexema()+" en algun ancestro");
            }
        }
    }

    private void consolidarMetodos(Clase claseAncestro) throws ExcepcionSemantica{ //TODO: preg si está bien hecho, cual de las dos versiones es mejor?
        //TODO: habria que controlar que no heredemos el metodo main si es q esta en un ancestro?

        /*
        for(Metodo metodo : claseAncestro.getMetodos()){
            if(this.existeMetodo(metodo)){
                //no hacer nada, lo redefinio
            } else{
                this.insertarMetodo(metodo.getTokenIdMet().getLexema(), metodo);
            }
        }
        */
        
        for(Metodo metodo : claseAncestro.getMetodos()){
            Metodo metodo_en_clase = metodos.get(metodo.getTokenIdMet().getLexema());
            if(metodo_en_clase != null){
                if(this.existeMetodo(metodo)){
                    //no hacer nada, lo esta redefiniendo
                } else{
                    throw new ExcepcionSemantica(metodo_en_clase.getTokenIdMet(), "la clase "+this.tokenIdClase.getLexema()+" redefine mal el metodo "+metodo_en_clase.getTokenIdMet().getLexema());
                }
            } else{
                this.insertarMetodo(metodo.getTokenIdMet().getLexema(), metodo);
            }
        }
        
    }

}