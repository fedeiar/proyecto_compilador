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

    public void insertarTipoParametrico(String nombreTipoParametrico, TipoClase tipoParametrico) throws ExcepcionSemantica{ //TODO: esta bien?
        if(tiposParametricos.get(nombreTipoParametrico) == null){
            tiposParametricos.put(nombreTipoParametrico, tipoParametrico);
            listaTiposParametricos.add(tipoParametrico);
        } else{
            throw new ExcepcionSemantica(tipoParametrico.getTokenIdClase(), "ya existe un tipo parametrico con el mismo nombre que "+tipoParametrico.getNombreTipo());
        }
    }

    public void verificarExistenciaTipo() throws ExcepcionSemantica{ //TODO: preg si está bien
        if(!TablaSimbolos.getInstance().existeClase(tokenIdClase.getLexema())){
            throw new ExcepcionSemantica(tokenIdClase, "la clase "+tokenIdClase.getLexema()+" no esta declarada");
        }
    }

    public boolean mismoTipo(TipoMetodo tipo){ //TODO: esta bien hecho asi?
        return tipo.visitarMismoTipo(this);
    }

    public boolean visitarMismoTipo(TipoClase tipo){ //TODO: esta bien hecho asi?
        return this.tokenIdClase.getLexema().equals(tipo.getTokenIdClase().getLexema());
    }

    public boolean verificarCompatibilidad(TipoMetodo tipo){
        return tipo.VisitarVerCompatibilidad(this);
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
