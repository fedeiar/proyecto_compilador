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
    private Constructor constructor;
    private Map<String, Atributo> atributos;
    private Map<String, Metodo> metodos;
    
    private boolean estaConsolidado;
    private boolean estaVerificadoHerenciaCircular; //TODO: usarlo para verificarHerenciaCircular

    public Clase(Token idClase){
        this.tokenIdClase = idClase;
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
        //debería verificar aca que exista la clase de la que heredo? NO, eso se verifica en el chequeo de declaraciones, junto a si hay herencia circular.
        this.tokenIdClaseAncestro = idClaseAncestro;
    }

    public void insertarAtributo(String nombreAtributo, Atributo atributo) throws ExcepcionSemantica{
        //TODO: deberia verificarse aca que no haya un ancesto que tenga un mismo atributo? no, se hace en la consolidacion
        if(atributos.get(nombreAtributo) == null){
            atributos.put(nombreAtributo, atributo);
        } else{
            throw new ExcepcionSemantica(atributo.getTokenIdVar());
        }
        
    }
    
    public void insertarConstructor(Constructor constructor) throws ExcepcionSemantica{
        //TODO: en que momento se verifica si la clase tiene o no constructor para asignarle uno con cuerpo vacio en caso de que no? se hace después del analisis sintactico.
        if(this.constructor == null){
            //TODO: para el logro va a haber que modificarlo y usar un hashMap como con metodos y atributos.
            this.constructor = constructor;
        } else{
            throw new ExcepcionSemantica(constructor.getTokenIdClase());
        }
    }

    public void insertarMetodo(String nombreMetodo, Metodo metodo) throws ExcepcionSemantica{
        //TODO: deberia verificarse aca si vamos a redefinir algun metodo de la clase ancesto o eso se hace después de que finaliza el sintactico? no, se hace en la consolidacion
        if(metodos.get(nombreMetodo) == null){
            metodos.put(nombreMetodo, metodo);
        } else{
            throw new ExcepcionSemantica(metodo.getTokenIdMet());
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

    public boolean existeNombreMetodo(String nombreMetodo2){
        if(metodos.get(nombreMetodo2) != null){
            return true;
        } else{
            return false;
        }
    }



    public void estaBienDeclarado() throws ExcepcionSemantica{
        if(!TablaSimbolos.getInstance().existeClase(this.tokenIdClaseAncestro.getLexema())){
            throw new ExcepcionSemantica(this.tokenIdClaseAncestro, "la clase "+this.tokenIdClaseAncestro.getLexema()+" de la cual se intenta heredar no esta declarada");
        }

        verificarHerenciaCircular(new HashMap<>());

        for(Atributo a : atributos.values()){
            a.estaBienDeclarado();
        }

        for(Metodo m : metodos.values()){
            m.estaBienDeclarado();
        }

        if(this.constructor == null){
            constructor = new Constructor(new Token(TipoDeToken.id_clase, this.tokenIdClase.getLexema(), 0)); //TODO: está bien creado el constructor por default?
        }

        //TODO: verificar otras cosas

    }

    public void verificarHerenciaCircular(Map<String, Clase> clases_ancestro) throws ExcepcionSemantica{
        if(!estaVerificadoHerenciaCircular){ //TODO: está bien cambiada la condición (this.tokenIdClaseAncestro != null) por la actual? sigue siendo correcto?
            if(clases_ancestro.get(this.tokenIdClase.getLexema()) != null){
                throw new ExcepcionSemantica(this.tokenIdClase, "la clase "+this.tokenIdClase.getLexema()+" hereda circularmente de "+ this.tokenIdClaseAncestro.getLexema());
            } else{
                clases_ancestro.put(this.tokenIdClase.getLexema(), this);
                verificarHerenciaCircular(clases_ancestro);
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
            this.insertarAtributo(atributo.getTokenIdVar().getLexema(), atributo);
        }
    }

    private void consolidarMetodos(Clase claseAncestro) throws ExcepcionSemantica{ //TODO: preg si está bien hecho
        for(Metodo metodo : claseAncestro.getMetodos()){
            if(this.existeNombreMetodo(metodo.getTokenIdMet().getLexema())){
                if(this.existeMetodo(metodo)){
                    //no hacer nada, lo esta redefiniendo
                } else{
                    Metodo metodoMalRedefinido = metodos.get(metodo.getTokenIdMet().getLexema());
                    throw new ExcepcionSemantica(metodoMalRedefinido.getTokenIdMet(), "la clase "+this.tokenIdClase.getLexema()+" redefine mal el metodo "+metodoMalRedefinido.getTokenIdMet().getLexema());
                }
            } else{
                this.insertarMetodo(metodo.getTokenIdMet().getLexema(), metodo);
            }
        }
        
    }

}
