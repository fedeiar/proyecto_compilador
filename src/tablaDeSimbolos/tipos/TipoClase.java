package tablaDeSimbolos.tipos;
import tablaDeSimbolos.entidades.Clase;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import analizadorLexico.Token;


public class TipoClase extends TipoConcreto{
    

    private Token tokenIdClase;
    private List<TipoClase> listaTiposParametricos;
    private Map<String, TipoClase> tiposParametricos;

    public TipoClase(Token tokenIdClase){
        this.tokenIdClase = tokenIdClase;
        tiposParametricos = new HashMap<>();
        listaTiposParametricos = new ArrayList<>();
    }

    public String getNombreTipo(){
        return tokenIdClase.getLexema();
    }

    public Token getTokenIdClase(){
        return tokenIdClase;
    }

    public void insertarTipoParametrico(String nombreTipoParametrico, TipoClase tipoParametrico) throws ExcepcionSemantica{
        
        tiposParametricos.put(nombreTipoParametrico, tipoParametrico);
        listaTiposParametricos.add(tipoParametrico);
        //TODO: faltan hacer los controles para logro, recordar que como aca se instancian SI pueden haber nombres repetidos (ver test2.java), asi que no se filtran los nombres repetidos
    }

    public void verificarExistenciaTipo() throws ExcepcionSemantica{  //TODO: esta bien el control de tipos parametricos pero hay que hacer mas controles, capaz cambia el codigo
        if(!TablaSimbolos.getInstance().existeClase(tokenIdClase.getLexema())){

            //if(!TablaSimbolos.claseActual.existeTipoParametrico(tokenIdClase)){ 
                throw new ExcepcionSemantica(tokenIdClase, "la clase "+tokenIdClase.getLexema()+" no esta declarada");
            //}
        }
    }

    public boolean mismoTipo(Tipo tipo){
        if(tipo instanceof TipoClase){
            TipoClase tipoClase = (TipoClase) tipo;
            return this.tokenIdClase.getLexema().equals(tipoClase.getTokenIdClase().getLexema());
        } else{
            return false;
        }  
    }

    public boolean soySubtipo(Tipo superTipo){
        if(superTipo instanceof TipoClase){
            TipoClase superTipoClase = (TipoClase) superTipo;
            return superTipoClase.esSubtipo(this);
        } else{
            return false;
        }
    }

    private boolean esSubtipo(TipoClase subtipo){ 
        if(this.tokenIdClase.getLexema().equals(subtipo.getTokenIdClase().getLexema())){
            return true;
        } else {
            Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
            Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
            if(tokenClasePadreDelSubtipo != null){
                return esSubtipo(new TipoClase(tokenClasePadreDelSubtipo));
            }else{
                return false;
            }
        }
    }

    public int distanciaPadre(Tipo superTipo){ //TODO: preguntar si esta bien.
        if(superTipo instanceof TipoClase){
            TipoClase superTipoClase = (TipoClase) superTipo;
            return superTipoClase.distanciaHijo(this);
        } else{
            return -1;
        }
    }

    private int distanciaHijo(TipoClase subtipo){
        if(this.tokenIdClase.getLexema().equals(subtipo.getTokenIdClase().getLexema())){
            return 0;
        } else{
            Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
            Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
            if(tokenClasePadreDelSubtipo != null){
                return 1 + distanciaHijo(new TipoClase(tokenClasePadreDelSubtipo));
            } else{
                return -1;
            }
        }
    }
    
}
