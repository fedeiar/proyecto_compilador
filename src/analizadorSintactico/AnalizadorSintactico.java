package analizadorSintactico;

import analizadorLexico.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AnalizadorSintactico {
    
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;

    private final List<TipoDeToken> primeros_miembro = Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private, TipoDeToken.id_clase, TipoDeToken.pr_static, TipoDeToken.pr_dynamic);
    private List<TipoDeToken> primeros_atributo = Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private);
    private List<TipoDeToken> primeros_metodo = Arrays.asList(TipoDeToken.pr_static, TipoDeToken.pr_dynamic);
    private List<TipoDeToken> primeros_tipoPrimitivo = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String);
    private List<TipoDeToken> primeros_tipo = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private List<TipoDeToken> primeros_listaArgsFormales = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private List<TipoDeToken> primeros_argFormal = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private List<TipoDeToken> primeros_sentencia = Arrays.asList(TipoDeToken.punt_puntoYComa, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new,
                                                    TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase, TipoDeToken.pr_return, 
                                                    TipoDeToken.pr_if, TipoDeToken.pr_for, TipoDeToken.punt_llaveDer);
    private List<TipoDeToken> primeros_acceso = Arrays.asList(TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private List<TipoDeToken> primeros_varLocal = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private List<TipoDeToken> primeros_tipoDeAsignacion = Arrays.asList(TipoDeToken.op_asignacion, TipoDeToken.op_incremento, TipoDeToken.op_decremento);

    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        this.analizadorLexico = analizadorLexico;
        tokenActual = analizadorLexico.proximoToken();
        inicial();
    }

    private void match(TipoDeToken nombreToken, String tokenQueSeEsperaba) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(nombreToken == tokenActual.getTipoDeToken()){
            tokenActual = analizadorLexico.proximoToken();
        }else{
            throw new ExcepcionSintactica(tokenActual, tokenQueSeEsperaba);
        }
    }

    private void inicial() throws IOException, ExcepcionLexica, ExcepcionSintactica {
        listaClases();
        match(TipoDeToken.EOF, "EOF");
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
        match(TipoDeToken.pr_class, "class");
        match(TipoDeToken.id_clase, "identificador de clase");
        herencia();
        match(TipoDeToken.punt_llaveIzq, "{");
        listaMiembros();
        match(TipoDeToken.punt_llaveDer, "}");
    }

    private void herencia() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_extends){
            match(TipoDeToken.pr_extends, "extends");
            match(TipoDeToken.id_clase, "identificador de clase");
        }else{
            //vacio
        }
    }

    private void listaMiembros() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_miembro.contains(tokenActual.getTipoDeToken())){
            miembro();
            listaMiembros();
        } else{
            //vacio
        }
    }

    private void miembro() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_atributo.contains(tokenActual.getTipoDeToken())){
            atributo();
        }else if(TipoDeToken.id_clase == tokenActual.getTipoDeToken()){
            constructor();
        }else if(primeros_metodo.contains(tokenActual.getTipoDeToken())){
            metodo();
        } else{
            throw new ExcepcionSintactica(tokenActual, "el comienzo de una declaración de un miembro (Metodo|Constructor|Atributo)");
        }
    }

    private void atributo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        visibilidad();
        tipo();
        listaDecAtrs();
    }

    private void metodo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        formaMetodo();
        tipoMetodo();
        match(TipoDeToken.id_metVar, "identificador de variable");
        argsFormales();
        bloque();
    }

    private void constructor() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.id_clase, "identificador de clase");
        argsFormales();
        bloque();
    }

    private void visibilidad() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_public){
            match(TipoDeToken.pr_public, "public");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_private){
            match(TipoDeToken.pr_private, "private");
        } else{
            throw new ExcepcionSintactica(tokenActual, "public o private");
        }
    }

    private void tipo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_tipoPrimitivo.contains(tokenActual.getTipoDeToken())){
            tipoPrimitivo();
        } else if (tokenActual.getTipoDeToken() == TipoDeToken.id_clase){
            match(TipoDeToken.id_clase, "identificador de clase");
        } else{
            throw new ExcepcionSintactica(tokenActual, "class o tipo primitivo");
        }
    }

    private void tipoPrimitivo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_boolean){
            match(TipoDeToken.pr_boolean, "boolean");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_char){
            match(TipoDeToken.pr_char, "char");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_int){
            match(TipoDeToken.pr_int, "int");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_String){
            match(TipoDeToken.pr_String, "String");
        } else{
            throw new ExcepcionSintactica(tokenActual, "un tipo primitivo");
        }
    }

    private void listaDecAtrs() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.id_metVar, "identificador de metodo o variable");
        listaDecAtrsFactorizada();
    }

    private void listaDecAtrsFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_coma){
            match(TipoDeToken.punt_coma, ";");
            listaDecAtrs();
        } else{
            //vacio
        }
    }

    private void formaMetodo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_static){
            match(TipoDeToken.pr_static, "static");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_dynamic){
            match(TipoDeToken.pr_dynamic, "dynamic");
        } else{
            throw new ExcepcionSintactica(tokenActual, "static o dynamic");
        }
    }

    private void tipoMetodo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_tipo.contains(tokenActual.getTipoDeToken())){
            tipo();
        } else if(TipoDeToken.pr_void == tokenActual.getTipoDeToken()){
            match(TipoDeToken.pr_void, "void");
        } else{
            throw new ExcepcionSintactica(tokenActual, "un tipo o void");
        }
    }

    private void argsFormales() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.punt_parentIzq, "(");
        listaArgsFormalesOVacio();
        match(TipoDeToken.punt_parentDer, ")");
    }

    private void listaArgsFormalesOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_listaArgsFormales.contains(tokenActual.getTipoDeToken())){
            listaArgsFormales();
        } else{
            //vacio
        }
    }

    private void listaArgsFormales() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        argFormal();
        listaArgsFormalesFactorizada();
    }

    private void listaArgsFormalesFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_coma){
            match(TipoDeToken.punt_coma, ",");
            listaArgsFormales();
        } else{
            //vacio
        }
    }

    private void argFormal() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        tipo();
        match(TipoDeToken.id_metVar, "identificador de metodo o variable");
    }

    private void bloque() throws IOException, ExcepcionLexica, ExcepcionSintactica{   
        match(TipoDeToken.punt_llaveIzq, "{");
        listaSentencias();
        match(TipoDeToken.punt_llaveDer, "}");
    }

    private void listaSentencias() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_sentencia.contains(tokenActual.getTipoDeToken())){
            sentencia();
            listaSentencias();
        } else{
            //vacio
        }
    }

    private void sentencia() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_puntoYComa){
            match(TipoDeToken.punt_puntoYComa, ";");
        } else if(primeros_acceso.contains(tokenActual.getTipoDeToken())){
            acceso();
            asignacion_llamada();
        } else if(primeros_varLocal.contains(tokenActual.getTipoDeToken())){
            varLocal();
            match(TipoDeToken.punt_puntoYComa, ";");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_return){
            return_();
            match(TipoDeToken.punt_puntoYComa, ";");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_if){
            if_();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_for){
            for_();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_llaveIzq){
            bloque();
        } else{
            throw new ExcepcionSintactica(tokenActual, "un ; o el comienzo de una sentencia");
        }
    }

    private void asignacion_llamada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_tipoDeAsignacion.contains(tokenActual.getTipoDeToken())){
            tipoDeAsignacion();
            match(TipoDeToken.punt_puntoYComa);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_puntoYComa){
            match(TipoDeToken.punt_puntoYComa);
        } else{
            throw new ExcepcionSintactica(tokenActual, "tipo de asignación o ;");
        }
    }

    private void asignacion() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        acceso();
        tipoDeAsignacion();
    }

    private void tipoDeAsignacion() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_asignacion){
            match(TipoDeToken.op_asignacion);
            expresion();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_incremento){
            match(TipoDeToken.op_incremento);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_decremento){
            match(TipoDeToken.op_decremento);
        } else{
            throw new ExcepcionSintactica(tokenActual, "asignacion, incremento o decremento");
        }
    }









    






}
