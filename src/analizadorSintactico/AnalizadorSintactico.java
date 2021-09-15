package analizadorSintactico;

import analizadorLexico.*;
import java.io.IOException;
import java.util.Arrays;

public class AnalizadorSintactico {
    
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;

    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws IOException, ExcepcionLexica{
        this.analizadorLexico = analizadorLexico;
        tokenActual = analizadorLexico.proximoToken();
        inicial();
    }


    private void match(TipoDeToken nombreToken) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(nombreToken == tokenActual.getTipoDeToken()){
            tokenActual = analizadorLexico.proximoToken();
        }else{
            throw new ExcepcionSintactica(tokenActual, nombreToken.toString());
        }
    }

    private void inicial() throws IOException, ExcepcionLexica, ExcepcionSintactica {
        listaClases();
    }

    private void listaClases() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        clase();
        listaClasesFactorizada();
    }

    private void listaClasesFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_class){
            listaClases();
        }else{
            //vacio
        }

    }

    private void clase() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_class);
        match(TipoDeToken.id_clase);
        herencia();
        match(TipoDeToken.punt_llaveIzq);
        listaMiembros();
        match(TipoDeToken.punt_llaveDer);
    }

    private void herencia() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_extends){
            match(TipoDeToken.pr_extends);
            match(TipoDeToken.id_clase);
        }else{
            //vacio
        }
    }

    private void listaMiembros() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private, TipoDeToken.id_clase, TipoDeToken.pr_static, TipoDeToken.pr_dynamic).contains(tokenActual.getTipoDeToken())){
            miembro();
            listaMiembros();
        } else{
            //vacio
        }
    }

    private void miembro() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private).contains(tokenActual.getTipoDeToken())){
            atributo();
        }else if(TipoDeToken.id_clase == tokenActual.getTipoDeToken()){
            constructor();
        }else if(Arrays.asList(TipoDeToken.pr_static, TipoDeToken.pr_dynamic).contains(tokenActual.getTipoDeToken())){
            metodo();
        } else{
            throw new ExcepcionSintactica(tokenActual, "el comienzo de una declaración de un miembro (Metodo|Constructor|Atributo)");
        }
    }

    






}
