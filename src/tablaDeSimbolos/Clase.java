package tablaDeSimbolos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;

public class Clase {
    

    private Token tokenIdClase;
    private Token tokenIdClaseAncestro;
    private List<Constructor> constructores;
    private Map<String, Atributo> atributos;
    private Map<String, Atributo> atributos_tapados;
    private Map<String, List<Metodo>> metodos;
    private List<Metodo> lista_metodos; //NO importa el orden de los elementos de esta lista.
    
    private boolean estaConsolidado;
    private boolean estaVerificadoHerenciaCircular;

    public Clase(Token idClase){
        this.tokenIdClase = idClase;
        constructores = new ArrayList<>();
        atributos = new HashMap<>();
        atributos_tapados = new HashMap<>();
        metodos = new HashMap<>();
        lista_metodos = new ArrayList<>();

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

    public Collection<Atributo> getAtributosTapados(){
        return atributos_tapados.values();
    }

    public List<Metodo> getMetodosMismoNombre(String nombreMetodo){
        return metodos.get(nombreMetodo);
    }

    public List<Metodo> getMetodos(){
        return lista_metodos;
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
        lista_metodos.add(metodo_a_insertar);
    }

    public Metodo getMetodoMismaSignatura(Metodo metodo2){ 
        for(Metodo metodo : lista_metodos){
            if(metodo.equalsSignatura(metodo2)){
                return metodo;
            }
        }
        return null;
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

            for(Metodo metodo : lista_metodos){
                metodo.estaBienDeclarado();
            }

            for(Constructor c : constructores){
                c.estaBienDeclarado();
            }

            if(constructores.size() == 0){
                insertarConstructor(this.tokenIdClase.getLexema(), new Constructor(new Token(TipoDeToken.id_clase, this.tokenIdClase.getLexema(), 0))); //TODO: está bien creado el constructor por default?
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
        for(Atributo atributo : claseAncestro.getAtributos()){
            Atributo atributo_en_clase = atributos.get(atributo.getTokenIdVar().getLexema());
            if(atributo_en_clase == null){
                this.insertarAtributo(atributo.getTokenIdVar().getLexema(), atributo);
            }
            else{
                atributos_tapados.put(atributo.getTokenIdVar().getLexema(), atributo); //TODO: preg si está bien. está bien no hacer controles? ya que no irán a parar repetidos aca.
                //throw new ExcepcionSemantica(atributo_en_clase.getTokenIdVar(), "ya existe un atributo con el mismo nombre que "+atributo_en_clase.getTokenIdVar().getLexema()+" en algun ancestro");
            }
        }
    }

    private void consolidarMetodos(Clase claseAncestro) throws ExcepcionSemantica{ //TODO: preguntar si asi esta bien.
        for(Metodo metodoAncestro : claseAncestro.getMetodos()){
            List<Metodo> lista_metodosMismoNombre = this.getMetodosMismoNombre(metodoAncestro.getTokenIdMet().getLexema());
            if(lista_metodosMismoNombre == null){
                this.insertarMetodo(metodoAncestro.getTokenIdMet().getLexema(), metodoAncestro);
            } else{
                boolean loSobrecarga = true;
                for(Metodo metodo_en_clase : lista_metodosMismoNombre){
                    if(metodo_en_clase.mismosParametros(metodoAncestro)){
                        if(metodo_en_clase.redefineCorrectamente(metodoAncestro)){
                            //no hacer nada, ya que lo redefine
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
