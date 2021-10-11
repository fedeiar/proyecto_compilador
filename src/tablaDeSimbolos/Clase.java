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
    private Map<String, Constructor> constructores;
    private Map<String, Atributo> atributos;
    private Map<String, Metodo> metodos;
    
    private boolean estaConsolidado;
    private boolean estaVerificadoHerenciaCircular;

    public Clase(Token idClase){
        this.tokenIdClase = idClase;
        constructores = new HashMap<>();
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
    
    public void insertarConstructor(String nombreConstructor, Constructor constructor) throws ExcepcionSemantica{
        Constructor constructor_en_clase = constructores.get(nombreConstructor);
        if(constructor_en_clase == null || !constructor_en_clase.mismosParametros(constructor)){ //TODO: preg si asi esta bien.
            constructores.put(nombreConstructor, constructor);
        } else{
            throw new ExcepcionSemantica(constructor.getTokenIdClase(), "existe otro constructor con el mismo nombre y parametros en esta clase"); 
        }
    }

    public void insertarMetodo(String nombreMetodo, Metodo metodo) throws ExcepcionSemantica{
        Metodo metodo_en_clase = metodos.get(nombreMetodo);
        if(metodo_en_clase == null || !metodo_en_clase.mismosParametros(metodo)){ //TODO: preg si con este chequeo alcanza.
            metodos.put(nombreMetodo, metodo);                                      //TODO: como hacer para que no colisionen 2 metodos con el mismo nombre en el hash?
        } else{
            throw new ExcepcionSemantica(metodo.getTokenIdMet(), "existe otro metodo con el mismo nombre y parametros en esta clase");
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

            if(constructores.size() == 0){
               insertarConstructor(this.tokenIdClase.getLexema(), new Constructor(new Token(TipoDeToken.id_clase, this.tokenIdClase.getLexema(), 0))); //TODO: está bien creado el constructor por default?
            }

            for(Constructor c : constructores.values()){
                c.estaBienDeclarado();
            }

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
        System.out.println("------------------------ le toca a: "+this.tokenIdClase.getLexema());
        for(Metodo metodo : claseAncestro.getMetodos()){
            System.out.println("el padre tiene el metodo: "+metodo.getTokenIdMet().getLexema());
            Metodo metodo_en_clase = metodos.get(metodo.getTokenIdMet().getLexema());
            if(metodo_en_clase == null || !metodo_en_clase.mismosParametros(metodo)){ //TODO: esta bien este chequeo para insertar los sobrecargados?
                this.insertarMetodo(metodo.getTokenIdMet().getLexema(), metodo); 
            } else{
                if(this.existeMetodo(metodo)){
                    //no hacer nada, lo esta redefiniendo
                } else{
                    throw new ExcepcionSemantica(metodo_en_clase.getTokenIdMet(), "la clase "+this.tokenIdClase.getLexema()+" redefine mal el metodo "+metodo_en_clase.getTokenIdMet().getLexema());
                }
            }
        }
        
    }

}
