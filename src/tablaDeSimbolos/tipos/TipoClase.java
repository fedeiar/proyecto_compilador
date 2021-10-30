package tablaDeSimbolos.tipos;
import tablaDeSimbolos.*;
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

    public boolean mismoTipo(TipoClase tipo){
        return this.tokenIdClase.getLexema().equals(tipo.getTokenIdClase().getLexema());
    }

    public boolean esSubtipo(Tipo tipoDelAncestro){
        return tipoDelAncestro.visitarEsSubtipo(this);
    }

    public boolean visitarEsSubtipo(TipoClase subtipo){ 
        if(this.tokenIdClase.getLexema().equals(subtipo.getTokenIdClase().getLexema())){
            return true;
        } else {
            Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
            Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
            if(tokenClasePadreDelSubtipo != null){
                return visitarEsSubtipo(new TipoClase(tokenClasePadreDelSubtipo));
            }else{
                return false;
            }
        }
    }

    public int profundidadDelHijo(TipoClase subtipo){ //TODO: preguntar si esta bien.
        if(this.tokenIdClase.getLexema().equals(subtipo.getTokenIdClase().getLexema())){
            return 0;
        } else {
            Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
            Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
            if(tokenClasePadreDelSubtipo != null){
                return 1 + profundidadDelHijo(new TipoClase(tokenClasePadreDelSubtipo));
            }else{
                return -1;
            }
        }
    }
    
}
