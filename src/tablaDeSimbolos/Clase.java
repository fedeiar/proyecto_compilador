package tablaDeSimbolos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Clase {
    

    private Token tokenIdClase;
    private Token tokenIdClaseAncestro;
    private List<Constructor> constructores;
    private Map<String, Atributo> atributos;
    private Map<String, List<Metodo>> metodos;
    
    private boolean estaConsolidado;
    private boolean estaVerificadoHerenciaCircular;

    public Clase(Token idClase){
        this.tokenIdClase = idClase;
        constructores = new ArrayList<>();
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

    public Token getTokenIdClaseAncestro(){
        return tokenIdClaseAncestro;
    }

    public Collection<Atributo> getAtributos(){
        return atributos.values();
    }

    public List<Metodo> getMetodosMismoNombre(String nombreMetodo){
        return metodos.get(nombreMetodo);
    }

    public Collection<List<Metodo>> getMetodos(){
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
    
    public void insertarConstructor(String nombreConstructor, Constructor constructor) throws ExcepcionSemantica{ //TODO: preg si esta bien.
        for(Constructor constructor_en_clase : constructores){
            if(constructor_en_clase.mismosParametros(constructor)){
                throw new ExcepcionSemantica(constructor.getTokenIdClase(), "existe otro constructor con el mismo nombre y parametros en esta clase"); 
            }
        }
        //si no hubo error, insertamos.
        constructores.add(constructor);
    }

    public void insertarMetodo(String nombreMetodo, Metodo metodo_a_insertar) throws ExcepcionSemantica{ //TODO: tener una lista que tenga todos los metodos al mismo nivel
        List<Metodo> lista_metodosMismoNombre = metodos.get(nombreMetodo);
        if(lista_metodosMismoNombre == null){      
            List<Metodo> nuevaLista_metodosMismoNombre = new ArrayList<>();
            nuevaLista_metodosMismoNombre.add(metodo_a_insertar);                                      
            metodos.put(nombreMetodo, nuevaLista_metodosMismoNombre);                                      
        } else{
            //Lo sobrecarga
            for(Metodo m : lista_metodosMismoNombre){
                if(m.mismosParametros(metodo_a_insertar)){
                    throw new ExcepcionSemantica(metodo_a_insertar.getTokenIdMet(), "existe otro metodo con el mismo nombre y parametros en esta clase");
                }
            }
            lista_metodosMismoNombre.add(metodo_a_insertar);
        }
    }

    public Metodo getMetodoMismaSignatura(Metodo metodo2){ //TODO: preg si esta bien.
        Metodo metodo_retorno = null;
        List<Metodo> lista_metodoEnClase = metodos.get(metodo2.getTokenIdMet().getLexema());
        if(lista_metodoEnClase != null){
            for(Metodo metodo : lista_metodoEnClase){
                if(metodo.equalsSignatura(metodo2)){
                    metodo_retorno = metodo;
                    break;
                }
            }
        }

        return metodo_retorno;
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

            for(List<Metodo> lista_metodosMismoNombre : metodos.values()){ 
                for(Metodo metodo : lista_metodosMismoNombre){
                    //TODO: tener un metodo que devuelva lista con TODOS los metodos al mismo nivel.
                    metodo.estaBienDeclarado();
                }
            }

            for(Constructor c : constructores){
                c.estaBienDeclarado();
            }

            if(constructores.size() == 0){
                insertarConstructor(this.tokenIdClase.getLexema(), new Constructor(new Token(TipoDeToken.id_clase, this.tokenIdClase.getLexema(), 0))); //TODO: está bien creado el constructor por default?
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
            estaVerificadoHerenciaCircular = true;
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

    private void consolidarMetodos(Clase claseAncestro) throws ExcepcionSemantica{ //TODO: tener una lista que tenga todos los metodos al mismo nivel

        for(List<Metodo> listaAncestro_metodosMismoNombre : claseAncestro.getMetodos()){
            for(Metodo metodoAncestro : listaAncestro_metodosMismoNombre){
                List<Metodo> lista_metodosMismoNombre = this.getMetodosMismoNombre(metodoAncestro.getTokenIdMet().getLexema());
                if(lista_metodosMismoNombre == null){
                    metodos.put(metodoAncestro.getTokenIdMet().getLexema(), listaAncestro_metodosMismoNombre);
                } else{
                    boolean loSobrecarga = true;
                    for(Metodo metodo_en_clase : lista_metodosMismoNombre){
                        if(metodo_en_clase.mismosParametros(metodoAncestro)){
                            if(metodo_en_clase.redefineCorrectamente(metodoAncestro)){
                                //no hacer nada, lo redefine
                                loSobrecarga = false;
                                break;
                            } else{
                                throw new ExcepcionSemantica(metodo_en_clase.getTokenIdMet(), "la clase "+this.tokenIdClase.getLexema()+" redefine mal el metodo "+metodo_en_clase.getTokenIdMet().getLexema());
                            }
                        }
                    }
                    if(loSobrecarga){
                        this.insertarMetodo(metodoAncestro.getTokenIdMet().getLexema(), metodoAncestro);
                       
                    }

                }
            }
        }
        
    }


}
