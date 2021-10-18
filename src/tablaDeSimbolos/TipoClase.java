package tablaDeSimbolos;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import analizadorLexico.Token;

public class TipoClase extends Tipo{
    

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
        //TODO: faltan hacer los controles, recordar que como aca se instancian SI pueden haber nombres repetidos (ver test2.java), asi que no se filtran los nombres repetidos
    }

    public void verificarExistenciaTipo() throws ExcepcionSemantica{  //TODO: esta bien el control de tipos parametricos pero hay que hacer mas controles, capaz cambia el codigo
        if(!TablaSimbolos.getInstance().existeClase(tokenIdClase.getLexema())){

            //if(!TablaSimbolos.claseActual.existeTipoParametrico(tokenIdClase)){ 
                throw new ExcepcionSemantica(tokenIdClase, "la clase "+tokenIdClase.getLexema()+" no esta declarada");
            //}
        }
    }

    public boolean mismoTipo(TipoMetodo tipo){
        return tipo.visitarMismoTipo(this);
    }

    public boolean visitarMismoTipo(TipoClase tipo){
        return this.tokenIdClase.getLexema().equals(tipo.getTokenIdClase().getLexema());
    }

    public boolean verificarCompatibilidad(TipoMetodo tipoDelAncestro){
        return tipoDelAncestro.VisitarVerCompatibilidad(this);
    }

    public boolean VisitarVerCompatibilidad(TipoClase subtipo){ 
        if(this.tokenIdClase.getLexema().equals(subtipo.getTokenIdClase().getLexema())){
            return true;
        } else {
            Clase claseDelSubtipo = TablaSimbolos.getClase(subtipo.getTokenIdClase().getLexema());
            Token tokenClasePadreDelSubtipo = claseDelSubtipo.getTokenIdClaseAncestro();
            if(tokenClasePadreDelSubtipo != null){
                return VisitarVerCompatibilidad(new TipoClase(tokenClasePadreDelSubtipo));
            }else{
                return false;
            }
        }
    }

    
    
}
