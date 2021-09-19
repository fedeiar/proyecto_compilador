package analizadorSintactico;

import analizadorLexico.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AnalizadorSintactico {
    
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;

    private final List<TipoDeToken> primeros_miembro = Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private, TipoDeToken.id_clase, TipoDeToken.pr_static, TipoDeToken.pr_dynamic);
    private final List<TipoDeToken> primeros_atributo = Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private);
    private final List<TipoDeToken> primeros_metodo = Arrays.asList(TipoDeToken.pr_static, TipoDeToken.pr_dynamic);
    private final List<TipoDeToken> primeros_tipoPrimitivo = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String);
    private final List<TipoDeToken> primeros_tipo = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private final List<TipoDeToken> primeros_listaArgsFormales = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private final List<TipoDeToken> primeros_sentencia = Arrays.asList(TipoDeToken.punt_puntoYComa, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new,
                                                         TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase, TipoDeToken.pr_return, 
                                                         TipoDeToken.pr_if, TipoDeToken.pr_for, TipoDeToken.punt_llaveDer);
    private final List<TipoDeToken> primeros_acceso = Arrays.asList(TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_varLocal = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private final List<TipoDeToken> primeros_tipoDeAsignacion = Arrays.asList(TipoDeToken.op_asignacion, TipoDeToken.op_incremento, TipoDeToken.op_decremento);
    private final List<TipoDeToken> primeros_expresion = Arrays.asList(TipoDeToken.op_suma, TipoDeToken.op_resta, TipoDeToken.op_not, TipoDeToken.pr_null, TipoDeToken.pr_true, TipoDeToken.pr_false,
                                                         TipoDeToken.lit_entero, TipoDeToken.lit_caracter, TipoDeToken.lit_string, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, 
                                                         TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_operadorBinario = Arrays.asList(TipoDeToken.op_or, TipoDeToken.op_and, TipoDeToken.op_igualdad, TipoDeToken.op_notIgual, TipoDeToken.op_menor, 
                                                               TipoDeToken.op_mayor, TipoDeToken.op_menorOIgual, TipoDeToken.op_mayorOIgual, TipoDeToken.op_suma, TipoDeToken.op_resta,
                                                               TipoDeToken.op_multiplicacion, TipoDeToken.op_division, TipoDeToken.op_modulo);
    private final List<TipoDeToken> primeros_operadorUnario = Arrays.asList(TipoDeToken.op_suma, TipoDeToken.op_resta, TipoDeToken.op_not);
    private final List<TipoDeToken> primeros_operando = Arrays.asList(TipoDeToken.pr_null, TipoDeToken.pr_true, TipoDeToken.pr_false, TipoDeToken.lit_entero, TipoDeToken.lit_caracter,
                                                        TipoDeToken.lit_string, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_literal = Arrays.asList(TipoDeToken.pr_null, TipoDeToken.pr_true, TipoDeToken.pr_false, TipoDeToken.lit_entero, TipoDeToken.lit_caracter, 
                                                       TipoDeToken.lit_string);
    private final List<TipoDeToken> primeros_primario = Arrays.asList(TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_expresionParentizada = Arrays.asList(TipoDeToken.op_suma, TipoDeToken.op_resta, TipoDeToken.op_not, TipoDeToken.pr_null, TipoDeToken.pr_true, 
                                                                    TipoDeToken.pr_false, TipoDeToken.lit_entero, TipoDeToken.lit_caracter, TipoDeToken.lit_string, TipoDeToken.punt_parentIzq,
                                                                    TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_argsActuales = Arrays.asList(TipoDeToken.punt_parentIzq);
    private final List<TipoDeToken> primeros_listaExps = Arrays.asList(TipoDeToken.op_suma, TipoDeToken.op_resta, TipoDeToken.op_not, TipoDeToken.pr_null, TipoDeToken.pr_true, TipoDeToken.pr_false,
                                                         TipoDeToken.lit_entero, TipoDeToken.lit_caracter, TipoDeToken.lit_string, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, 
                                                         TipoDeToken.pr_this, TipoDeToken.pr_new);


    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        this.analizadorLexico = analizadorLexico;
        tokenActual = analizadorLexico.proximoToken();
        inicial();
    }

    private void match(TipoDeToken nombreToken, String tokenQueSeEsperaba) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(nombreToken == tokenActual.getTipoDeToken()){
            tokenActual = analizadorLexico.proximoToken();
        } else{
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
        } else{
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
            match(TipoDeToken.punt_puntoYComa, ";");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_puntoYComa){
            match(TipoDeToken.punt_puntoYComa, ";");
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
            match(TipoDeToken.op_asignacion, "=");
            expresion();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_incremento){
            match(TipoDeToken.op_incremento, "++");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_decremento){
            match(TipoDeToken.op_decremento, "--");
        } else{
            throw new ExcepcionSintactica(tokenActual, "asignacion, incremento o decremento");
        }
    }

    private void varLocal() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        tipo();
        match(TipoDeToken.id_metVar, "identificador de metodo o variable");
        varLocalFactorizada();
    }

    private void varLocalFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_asignacion){
            match(TipoDeToken.op_asignacion, "=");
            expresion();
        } else{
            //vacio
        }
    }

    private void return_() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_return, "return");
        expresionOVacio();
    }

    private void expresionOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_expresion.contains(tokenActual.getTipoDeToken())){
            expresion();
        } else{
            //vacio
        }
    }

    private void if_() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        //TODO: ver el tema de la ambiguedad del if. Hay que hacer algo o ya está resuelto?
        match(TipoDeToken.pr_if, "if");
        match(TipoDeToken.punt_parentIzq, "(");
        expresion();
        match(TipoDeToken.punt_parentDer, ")");
        sentencia();
        ifFactorizado();
    }

    private void ifFactorizado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_else){
            match(TipoDeToken.pr_else, "else");
            sentencia();
        } else{
            //vacio
        }
    }

    private void for_() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_for, "for");
        match(TipoDeToken.punt_parentIzq, "(");
        varLocal();
        match(TipoDeToken.punt_puntoYComa, ";");
        expresion();
        match(TipoDeToken.punt_puntoYComa, ";");
        asignacion();
        match(TipoDeToken.punt_parentDer, ")");
        sentencia();
    }

    private void expresion() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        expresionUnaria();
        expresionRecursiva();
    }

    private void expresionRecursiva() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_operadorBinario.contains(tokenActual.getTipoDeToken())){
            operadorBinario();
            expresionUnaria();
            expresionRecursiva();
        } else{
            //vacio
        }
    }

    private void operadorBinario() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_or){
            match(TipoDeToken.op_or, "||");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_and){
            match(TipoDeToken.op_and, "&&");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_igualdad){
            match(TipoDeToken.op_igualdad, "==");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_notIgual){
            match(TipoDeToken.op_notIgual, "!=");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menor){
            match(TipoDeToken.op_menor, "<");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_mayor){
            match(TipoDeToken.op_mayor, ">");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menorOIgual){
            match(TipoDeToken.op_menorOIgual, "<=");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_mayorOIgual){
            match(TipoDeToken.op_mayorOIgual, ">=");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_suma){
            match(TipoDeToken.op_suma, "+");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_resta){
            match(TipoDeToken.op_resta, "-");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_multiplicacion){
            match(TipoDeToken.op_multiplicacion, "*");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_division){
            match(TipoDeToken.op_division, "/");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_modulo){
            match(TipoDeToken.op_modulo, "%");
        } else {
            throw new ExcepcionSintactica(tokenActual, "un operador binario");
        }
        
    }

    private void expresionUnaria() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_operadorUnario.contains(tokenActual.getTipoDeToken())){
            operadorUnario();
            operando();
        } else if(primeros_operando.contains(tokenActual.getTipoDeToken())){
            operando();
        } else{
            throw new ExcepcionSintactica(tokenActual, "operador unario u operando");
        }
    }

    private void operadorUnario() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_suma){
            match(TipoDeToken.op_suma, "+");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_resta){
            match(TipoDeToken.op_resta, "-");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_notIgual){
            match(TipoDeToken.op_notIgual, "!");
        } else{
            throw new ExcepcionSintactica(tokenActual, "operador unario");
        }
    }

    private void operando() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_literal.contains(tokenActual.getTipoDeToken())){
            literal();
        } else if(primeros_acceso.contains(tokenActual.getTipoDeToken())){
            acceso();
        } else{
            throw new ExcepcionSintactica(tokenActual, "un literal o el comienzo de un acceso");
        }
    }

    private void literal() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_null){
            match(TipoDeToken.pr_null, "null");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_true){
            match(TipoDeToken.pr_true, "true");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_false){
            match(TipoDeToken.pr_false, "false");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.lit_entero){
            match(TipoDeToken.lit_entero, "literal entero");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.lit_caracter){
            match(TipoDeToken.lit_caracter, "literal caracter");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.lit_string){
            match(TipoDeToken.lit_string, "literal String");
        } else{
            throw new ExcepcionSintactica(tokenActual, "un literal");
        }
    }

    private void acceso() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_parentIzq){
            match(TipoDeToken.punt_parentIzq, "(");
            casting_expresionParentizada();
        } else if(primeros_primario.contains(tokenActual.getTipoDeToken())){
            primario();
            encadenado();
        } else{
            throw new ExcepcionSintactica(tokenActual, "el comienzo de un primario o casting");
        }
    }

    private void casting_expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.id_clase){
            match(TipoDeToken.id_clase, "identificador de clase");
            match(TipoDeToken.punt_parentDer, ")");
            primario_expresionParentizada();
            encadenado();
        } else if(primeros_expresionParentizada.contains(tokenActual.getTipoDeToken())){
            expresionParentizada();
            encadenado();
        } else{
            throw new ExcepcionSintactica(tokenActual, "un identificador de clase o una expresion");
        }
    }

    private void primario() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.id_metVar){
            match(TipoDeToken.id_metVar, "identificador de metodo o variable");
            accesoVar_accesoMetodo();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_this){
            match(TipoDeToken.pr_this, "this");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_new){
            accesoConstructor();
        } else{
            throw new ExcepcionSintactica(tokenActual, "un primario");
        }
    }

    private void primario_expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_primario.contains(tokenActual.getTipoDeToken())){
            primario();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_parentIzq){
            match(TipoDeToken.punt_parentIzq, "(");
            expresionParentizada();
        } else{
            throw new ExcepcionSintactica(tokenActual, "el comienzo de un primario");
        }
    }

    private void expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        expresion();
        match(TipoDeToken.punt_parentDer, ")");
    }

    private void accesoVar_accesoMetodo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_argsActuales.contains(tokenActual.getTipoDeToken())){
            argsActuales();
        } else{
            //vacio
        }
    }

    private void accesoConstructor() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_new, "new");
        match(TipoDeToken.id_clase, "identificador de clase");
        argsActuales();
    }

    private void argsActuales() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.punt_parentIzq, "(");
        listaExpsOVacio();
        match(TipoDeToken.punt_parentDer, ")");
    }

    private void listaExpsOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_listaExps.contains(tokenActual.getTipoDeToken())){
            listaExps();
        } else{
            //vacio
        }
    }

    private void listaExps() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        expresion();
        listaExpsFactorizada();
    }

    private void listaExpsFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_puntoYComa){
            match(TipoDeToken.punt_coma, ",");
            listaExps();
        } else{
            //vacio
        }
    }

    private void encadenado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_punto){
            varOMetodoEncadenado();
            encadenado();
        } else{
            //vacio
        }
    }

    private void varOMetodoEncadenado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.punt_punto, ".");
        match(TipoDeToken.id_metVar, "identificador de metodo o variable");
        varOMetodoEncadenadoFactorizado();
    }

    private void varOMetodoEncadenadoFactorizado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_argsActuales.contains(tokenActual.getTipoDeToken())){
            argsActuales();
        } else{
            //vacio
        }
    }













    






}
