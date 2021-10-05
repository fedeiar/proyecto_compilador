package tablaDeSimbolos;
import java.util.HashMap;
import java.util.Map;

import analizadorLexico.Token;

public class Clase {
    

    private Token idClase;
    private Token idClaseAncestro;
    private Constructor constructor;
    private Map<String, Atributo> atributos;
    private Map<String, Metodo> metodos;
    

    public Clase(Token idClase){
        this.idClase = idClase;
    }

    public Token getTokenIdClase(){
        return idClase;
    }

    public void set_idClaseAncestro(Token idClaseAncestro){
        //debería verificar aca que exista la clase de la que heredo? NO, eso se verifica en el chequeo de declaraciones, junto a si hay herencia circular.
        this.idClaseAncestro = idClaseAncestro;
    }

    public void establecerConstructor(Constructor constructor) throws ExcepcionSemantica{
        //TODO: en que momento se verifica si la clase tiene o no constructor para asignarle uno con cuerpo vacio en caso de que no? se hace después del analisis sintactico.
        if(this.constructor == null){
            //TODO: para el logro va a haber que modificarlo
            this.constructor = constructor;
        } else{
            throw new ExcepcionSemantica(constructor.getTokenIdClase());
        }
    }

    public void insertarAtributo(String nombreAtributo, Atributo atributo) throws ExcepcionSemantica{
        //TODO: deberia verificarse aca que no haya un ancesto que tenga un mismo atributo? no, se hace en la consolidacion
        if(atributos.get(nombreAtributo) == null){
            atributos.put(nombreAtributo, atributo);
        } else{
            throw new ExcepcionSemantica(atributo.getTokenIdVar());
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

    


    public Metodo existeMetodo(Token tokenIdMet) throws ExcepcionSemantica{
        Metodo metodo = metodos.get(tokenIdMet.getLexema());
        if(metodo != null){
            return metodo;
        } else{
            throw new ExcepcionSemantica(tokenIdMet);
        }
    }

}
