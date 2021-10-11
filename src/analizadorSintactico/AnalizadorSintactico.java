package analizadorSintactico;

import analizadorLexico.*;
import tablaDeSimbolos.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AnalizadorSintactico {
    
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;

    //Primeros

    private final List<TipoDeToken> primeros_miembro = Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private, TipoDeToken.id_clase, TipoDeToken.pr_static, TipoDeToken.pr_dynamic,
                                                       TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String);
    private final List<TipoDeToken> primeros_atributo = Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private, TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, 
                                                        TipoDeToken.pr_String);
    private final List<TipoDeToken> primeros_metodo = Arrays.asList(TipoDeToken.pr_static, TipoDeToken.pr_dynamic);
    private final List<TipoDeToken> primeros_visibilidad = Arrays.asList(TipoDeToken.pr_public, TipoDeToken.pr_private);
    private final List<TipoDeToken> primeros_tipoPrimitivo = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String);
    private final List<TipoDeToken> primeros_listaDecAtrs = Arrays.asList(TipoDeToken.id_metVar);
    private final List<TipoDeToken> primeros_tipo = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private final List<TipoDeToken> primeros_argsFormales = Arrays.asList(TipoDeToken.punt_parentIzq);
    private final List<TipoDeToken> primeros_argFormal = primeros_tipo;
    private final List<TipoDeToken> primeros_listaArgsFormales = primeros_argFormal;
    private final List<TipoDeToken> primeros_sentencia = Arrays.asList(TipoDeToken.punt_puntoYComa, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new,
                                                         TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase, TipoDeToken.pr_return, 
                                                         TipoDeToken.pr_if, TipoDeToken.pr_for, TipoDeToken.punt_llaveIzq);
    private final List<TipoDeToken> primeros_acceso = Arrays.asList(TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_varLocal = Arrays.asList(TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase);
    private final List<TipoDeToken> primeros_varLocalFactorizada = Arrays.asList(TipoDeToken.op_asignacion); // ojo! contiene a vacio
    private final List<TipoDeToken> primeros_tipoDeAsignacion = Arrays.asList(TipoDeToken.op_asignacion, TipoDeToken.op_incremento, TipoDeToken.op_decremento);
    private final List<TipoDeToken> primeros_expresion = Arrays.asList(TipoDeToken.op_mas, TipoDeToken.op_menos, TipoDeToken.op_not, TipoDeToken.pr_null, TipoDeToken.pr_true, TipoDeToken.pr_false,
                                                         TipoDeToken.lit_entero, TipoDeToken.lit_caracter, TipoDeToken.lit_string, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, 
                                                         TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_operadorBinario = Arrays.asList(TipoDeToken.op_or, TipoDeToken.op_and, TipoDeToken.op_igualdad, TipoDeToken.op_notIgual, TipoDeToken.op_menor, 
                                                               TipoDeToken.op_mayor, TipoDeToken.op_menorOIgual, TipoDeToken.op_mayorOIgual, TipoDeToken.op_mas, TipoDeToken.op_menos,
                                                               TipoDeToken.op_multiplicacion, TipoDeToken.op_division, TipoDeToken.op_modulo);
    private final List<TipoDeToken> primeros_operadorUnario = Arrays.asList(TipoDeToken.op_mas, TipoDeToken.op_menos, TipoDeToken.op_not);
    private final List<TipoDeToken> primeros_operando = Arrays.asList(TipoDeToken.pr_null, TipoDeToken.pr_true, TipoDeToken.pr_false, TipoDeToken.lit_entero, TipoDeToken.lit_caracter,
                                                        TipoDeToken.lit_string, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_literal = Arrays.asList(TipoDeToken.pr_null, TipoDeToken.pr_true, TipoDeToken.pr_false, TipoDeToken.lit_entero, TipoDeToken.lit_caracter, 
                                                       TipoDeToken.lit_string);
    private final List<TipoDeToken> primeros_primario = Arrays.asList(TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new);
    private final List<TipoDeToken> primeros_expresionParentizada = primeros_expresion;
    private final List<TipoDeToken> primeros_argsActuales = Arrays.asList(TipoDeToken.punt_parentIzq);
    private final List<TipoDeToken> primeros_listaExps = primeros_expresion;
    private final List<TipoDeToken> primeros_tipoParametricoOVacio = Arrays.asList(TipoDeToken.op_menor); //ojo! contiene a vacio
    private final List<TipoDeToken> primeros_tipoParametrico = Arrays.asList(TipoDeToken.id_clase);

    //Siguientes

    private final List<TipoDeToken> siguientes_listaClasesFactorizada = Arrays.asList(TipoDeToken.EOF);
    private final List<TipoDeToken> siguientes_herencia = Arrays.asList(TipoDeToken.punt_llaveIzq);
    private final List<TipoDeToken> siguientes_listaMiembros = Arrays.asList(TipoDeToken.punt_llaveDer);
    private final List<TipoDeToken> siguientes_listaDecAtrsFactorizada = Arrays.asList(TipoDeToken.punt_puntoYComa);
    private final List<TipoDeToken> siguientes_listaArgsFormalesOVacio = Arrays.asList(TipoDeToken.punt_parentDer);
    private final List<TipoDeToken> siguientes_listaArgsFormalesFactorizada = Arrays.asList(TipoDeToken.punt_parentDer);
    private final List<TipoDeToken> siguientes_listaSentencias = Arrays.asList(TipoDeToken.punt_llaveDer);
    private final List<TipoDeToken> siguientes_varLocalFactorizada = Arrays.asList(TipoDeToken.punt_puntoYComa);
    private final List<TipoDeToken> siguientes_expresionOVacio = Arrays.asList(TipoDeToken.punt_puntoYComa);
    private final List<TipoDeToken> siguientes_ifFactorizado = Arrays.asList(TipoDeToken.punt_puntoYComa, TipoDeToken.punt_parentIzq, TipoDeToken.id_metVar, TipoDeToken.pr_this, TipoDeToken.pr_new,
                                                               TipoDeToken.pr_boolean, TipoDeToken.pr_char, TipoDeToken.pr_int, TipoDeToken.pr_String, TipoDeToken.id_clase, TipoDeToken.pr_return, 
                                                               TipoDeToken.pr_if, TipoDeToken.pr_for, TipoDeToken.punt_llaveIzq, TipoDeToken.pr_else, TipoDeToken.punt_llaveDer); 
    private final List<TipoDeToken> siguientes_expresionRecursiva = Arrays.asList(TipoDeToken.punt_puntoYComa, TipoDeToken.punt_coma,TipoDeToken.punt_parentDer);
    private final List<TipoDeToken> siguientes_accesoVar_accesoMetodo = Arrays.asList(TipoDeToken.punt_punto, TipoDeToken.op_asignacion, TipoDeToken.op_incremento, TipoDeToken.op_decremento, 
                                                                        TipoDeToken.punt_puntoYComa, TipoDeToken.op_or, TipoDeToken.op_and, TipoDeToken.op_igualdad, TipoDeToken.op_notIgual, 
                                                                        TipoDeToken.op_menor, TipoDeToken.op_mayor, TipoDeToken.op_menorOIgual, TipoDeToken.op_mayorOIgual, TipoDeToken.op_mas, 
                                                                        TipoDeToken.op_menos, TipoDeToken.op_multiplicacion, TipoDeToken.op_division,TipoDeToken.op_modulo, TipoDeToken.punt_coma, 
                                                                        TipoDeToken.punt_parentDer);
    private final List<TipoDeToken> siguientes_listaExpsOVacio = Arrays.asList(TipoDeToken.punt_parentDer);
    private final List<TipoDeToken> siguientes_listaExpsFactorizada = Arrays.asList(TipoDeToken.punt_parentDer);
    private final List<TipoDeToken> siguientes_encadenado = Arrays.asList(TipoDeToken.op_asignacion, TipoDeToken.op_incremento, TipoDeToken.op_decremento, TipoDeToken.punt_puntoYComa, TipoDeToken.op_or,
                                                            TipoDeToken.op_and, TipoDeToken.op_igualdad, TipoDeToken.op_notIgual, TipoDeToken.op_menor, TipoDeToken.op_mayor, TipoDeToken.op_menorOIgual,
                                                            TipoDeToken.op_mayorOIgual, TipoDeToken.op_mas, TipoDeToken.op_menos, TipoDeToken.op_multiplicacion, TipoDeToken.op_division, 
                                                            TipoDeToken.op_modulo, TipoDeToken.punt_coma, TipoDeToken.punt_parentDer); 
    private final List<TipoDeToken> siguientes_varOMetodoEncadenado = Arrays.asList(TipoDeToken.punt_punto, TipoDeToken.op_asignacion, TipoDeToken.op_incremento, TipoDeToken.op_decremento, 
                                                                      TipoDeToken.punt_puntoYComa, TipoDeToken.op_or, TipoDeToken.op_and, TipoDeToken.op_igualdad, TipoDeToken.op_notIgual, 
                                                                      TipoDeToken.op_menor, TipoDeToken.op_mayor, TipoDeToken.op_menorOIgual, TipoDeToken.op_mayorOIgual, TipoDeToken.op_mas, 
                                                                      TipoDeToken.op_menos, TipoDeToken.op_multiplicacion, TipoDeToken.op_division, TipoDeToken.op_modulo, TipoDeToken.punt_coma, 
                                                                      TipoDeToken.punt_parentDer);

    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        this.analizadorLexico = analizadorLexico;
        tokenActual = analizadorLexico.proximoToken();
        inicial();
    }

    private void match(TipoDeToken nombreTokenEsperado, String tokenQueSeEsperaba) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(nombreTokenEsperado == tokenActual.getTipoDeToken()){
            tokenActual = analizadorLexico.proximoToken();
        } else{
            throw new ExcepcionSintactica(tokenQueSeEsperaba, tokenActual);
        }
    }

    private void inicial() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        listaClases();
        match(TipoDeToken.EOF, "EOF");
    }

    private void listaClases() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        clase();
        listaClasesFactorizada();
    }

    private void listaClasesFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_class){
            listaClases();
        } else if(siguientes_listaClasesFactorizada.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("class o el fin del archivo", tokenActual);
        }

    }

    private void clase() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        match(TipoDeToken.pr_class, "class");
        Token tokenIdClase = tokenActual;
        match(TipoDeToken.id_clase, "identificador de clase");
        Clase clase = new Clase(tokenIdClase);
        TablaSimbolos.getInstance().claseActual = clase;
        TablaSimbolos.getInstance().insertarClase(tokenIdClase.getLexema(), clase);

        tipoParametricoOVacio(); //TODO: HACER ALGO CON ESTO

        Token tokenIdClaseAncestro = herencia();
        clase.set_idClaseAncestro(tokenIdClaseAncestro);

        match(TipoDeToken.punt_llaveIzq, "{");
        listaMiembros();
        match(TipoDeToken.punt_llaveDer, "}");
    }

    private Token herencia() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_extends){
            match(TipoDeToken.pr_extends, "extends");
            Token tokenIdClase = tokenActual;
            match(TipoDeToken.id_clase, "identificador de clase");
            
            tipoParametricoOVacio(); //TODO: HACER ALGO CON ESTO

            return tokenIdClase;
        }else if(siguientes_herencia.contains(tokenActual.getTipoDeToken())){
            return new Token(TipoDeToken.id_clase, "Object", 0);
        } else{
            throw new ExcepcionSintactica("{ o extends", tokenActual);
        }
    }

    private void tipoParametricoOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(TipoDeToken.op_menor == tokenActual.getTipoDeToken()){
            match(TipoDeToken.op_menor, "<");
            tipoParametrico();
            listaTiposParametricos();
            match(TipoDeToken.op_mayor, ">");
        } else{
            //vacio
        }
    }

    private void listaTiposParametricos() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(TipoDeToken.punt_coma == tokenActual.getTipoDeToken()){
            match(TipoDeToken.punt_coma, ",");
            tipoParametrico();
            listaTiposParametricos();
        } else{
            //vacio
        }
    }

    private void tipoParametrico() throws IOException, ExcepcionLexica, ExcepcionSintactica{      
        match(TipoDeToken.id_clase, "identificador de clase");
        tipoParametricoOVacio();
    }

    private void listaMiembros() throws IOException, ExcepcionLexica, ExcepcionSintactica,  ExcepcionSemantica{
        if(primeros_miembro.contains(tokenActual.getTipoDeToken())){
            miembro();
            listaMiembros();
        } else if(siguientes_listaMiembros.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("el comienzo de un miembro o }", tokenActual);
        }
    }

    private void miembro() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(TipoDeToken.id_clase == tokenActual.getTipoDeToken()){
            Token tokenIdClase = tokenActual;
            match(TipoDeToken.id_clase, "identificador de clase");
            constructor_atributoImplicito(tokenIdClase);
        } else if(primeros_atributo.contains(tokenActual.getTipoDeToken())){
            atributo();
        }else if(primeros_metodo.contains(tokenActual.getTipoDeToken())){
            metodo();
        } else{
            throw new ExcepcionSintactica("el comienzo de una declaración de un miembro (Metodo|Constructor|Atributo)", tokenActual);
        }
    }

    private void constructor_atributoImplicito(Token tokenIdClase) throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(primeros_argsFormales.contains(tokenActual.getTipoDeToken())){
            Constructor constructor = new Constructor(tokenIdClase);
            TablaSimbolos.unidadActual = constructor;
            argsFormales();
            TablaSimbolos.claseActual.insertarConstructor(tokenIdClase.getLexema(), constructor); //TODO: esta bien que haga falta hacerlo despues de argsFormales()?
            bloque();
        } else if(primeros_tipoParametricoOVacio.contains(tokenActual.getTipoDeToken()) || primeros_listaDecAtrs.contains(tokenActual.getTipoDeToken())){
            tipoParametricoOVacio(); //TODO: HACER ALGO CON ESTO
            listaDecAtrs(TipoDeToken.pr_public, new TipoClase(tokenIdClase));
            match(TipoDeToken.punt_puntoYComa, ";");
        } else{
            throw new ExcepcionSintactica("un (, un < o un identificador de variable", tokenActual);
        }
    }

    private void atributo() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(primeros_visibilidad.contains(tokenActual.getTipoDeToken())){
            TipoDeToken visibilidad = visibilidad();
            Tipo tipo = tipo();
            listaDecAtrs(visibilidad, tipo);
            match(TipoDeToken.punt_puntoYComa, ";");
        } else if(primeros_tipoPrimitivo.contains(tokenActual.getTipoDeToken())){
            TipoDeToken visibilidad = TipoDeToken.pr_public;
            Tipo tipo = tipoPrimitivo();
            listaDecAtrs(visibilidad, tipo);
            match(TipoDeToken.punt_puntoYComa, ";");
        } else{
            throw new ExcepcionSintactica("el comienzo de la declaracion un atributo", tokenActual);
        }
    }

    private void metodo() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        TipoDeToken formaMetodo = formaMetodo();
        TipoMetodo tipoMetodo = tipoMetodo();
        Token tokenIdMet = tokenActual;
        match(TipoDeToken.id_metVar, "identificador de metodo");
        Metodo metodo = new Metodo(tokenIdMet, formaMetodo, tipoMetodo);
        TablaSimbolos.getInstance().unidadActual = metodo;
        argsFormales();
        TablaSimbolos.getInstance().claseActual.insertarMetodo(tokenIdMet.getLexema(), metodo); //TODO: puede ser que haga falta hacer esto desp. de argsFormales()?
        bloque();
    }

    private TipoDeToken visibilidad() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_public){
            TipoDeToken visibilidad = tokenActual.getTipoDeToken();
            match(TipoDeToken.pr_public, "public");
            return visibilidad;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_private){
            TipoDeToken visibilidad = tokenActual.getTipoDeToken();
            match(TipoDeToken.pr_private, "private");
            return visibilidad;
        } else{
            throw new ExcepcionSintactica("public o private", tokenActual);
        }
    }

    private Tipo tipo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_tipoPrimitivo.contains(tokenActual.getTipoDeToken())){
            Tipo tipo = tipoPrimitivo();
            return tipo;
        } else if (tokenActual.getTipoDeToken() == TipoDeToken.id_clase){
            Token tokenIdClase = tokenActual;
            match(TipoDeToken.id_clase, "identificador de clase");
            Tipo tipo = new TipoClase(tokenIdClase);

            tipoParametricoOVacio(); //TODO: HACER ALGO CON ESTO

            return tipo;
        } else{
            throw new ExcepcionSintactica("identificador de clase o tipo primitivo", tokenActual);
        }
    }

    private Tipo tipoPrimitivo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_boolean){
            match(TipoDeToken.pr_boolean, "boolean");
            return new TipoBoolean();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_char){
            match(TipoDeToken.pr_char, "char");
            return new TipoChar();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_int){
            match(TipoDeToken.pr_int, "int");
            return new TipoInt();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_String){
            match(TipoDeToken.pr_String, "String");
            return new TipoString();
        } else{
            throw new ExcepcionSintactica("un tipo primitivo", tokenActual);
        }
    }

    private void listaDecAtrs(TipoDeToken visibilidad, Tipo tipoAtributo) throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        Token tokenIdVar = tokenActual;
        match(TipoDeToken.id_metVar, "identificador de metodo o variable");
        Atributo atributo = new Atributo(tokenIdVar, visibilidad, tipoAtributo);
        TablaSimbolos.getInstance().claseActual.insertarAtributo(tokenIdVar.getLexema(), atributo);
        listaDecAtrsFactorizada(visibilidad, tipoAtributo);
    }

    private void listaDecAtrsFactorizada(TipoDeToken visibilidad, Tipo tipoAtributo) throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_coma){
            match(TipoDeToken.punt_coma, ",");
            listaDecAtrs(visibilidad, tipoAtributo);
        } else if(siguientes_listaDecAtrsFactorizada.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("un , o ;", tokenActual);
        }
    }

    private TipoDeToken formaMetodo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_static){
            TipoDeToken formaMetodo = tokenActual.getTipoDeToken();
            match(TipoDeToken.pr_static, "static");
            return formaMetodo;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_dynamic){
            TipoDeToken formaMetodo = tokenActual.getTipoDeToken();
            match(TipoDeToken.pr_dynamic, "dynamic");
            return formaMetodo;
        } else{
            throw new ExcepcionSintactica("static o dynamic", tokenActual);
        }
    }

    private TipoMetodo tipoMetodo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_tipo.contains(tokenActual.getTipoDeToken())){
            Tipo tipo = tipo();
            return tipo;
        } else if(TipoDeToken.pr_void == tokenActual.getTipoDeToken()){
            match(TipoDeToken.pr_void, "void");
            return new TipoVoid();
        } else{
            throw new ExcepcionSintactica("un tipo o void", tokenActual);
        }
    }

    private void argsFormales() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        match(TipoDeToken.punt_parentIzq, "(");
        listaArgsFormalesOVacio();
        match(TipoDeToken.punt_parentDer, ")");
    }

    private void listaArgsFormalesOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(primeros_listaArgsFormales.contains(tokenActual.getTipoDeToken())){
            listaArgsFormales();
        } else if(siguientes_listaArgsFormalesOVacio.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("un tipo o )", tokenActual);
        }
    }

    private void listaArgsFormales() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        argFormal();
        listaArgsFormalesFactorizada();
    }

    private void listaArgsFormalesFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_coma){
            match(TipoDeToken.punt_coma, ",");
            listaArgsFormales();
        } else if(siguientes_listaArgsFormalesFactorizada.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("un ; o )", tokenActual);
        }
    }

    private void argFormal() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        Tipo tipo = tipo();
        Token tokenIdVar = tokenActual;
        match(TipoDeToken.id_metVar, "identificador de metodo o variable");
        ParametroFormal parametro = new ParametroFormal(tokenIdVar, tipo);
        TablaSimbolos.getInstance().unidadActual.insertarParametro(tokenIdVar.getLexema(), parametro);
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
        } else if(siguientes_listaSentencias.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("una sentencia o }", tokenActual);
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
            match(TipoDeToken.pr_for, "for");
            match(TipoDeToken.punt_parentIzq, "(");
            tipo();
            match(TipoDeToken.id_metVar, "identificador de variable");
            for_forEach();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_llaveIzq){
            bloque();
        } else{
            throw new ExcepcionSintactica("un ; o el comienzo de una sentencia", tokenActual);
        }
    }

    private void asignacion_llamada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_tipoDeAsignacion.contains(tokenActual.getTipoDeToken())){
            tipoDeAsignacion();
            match(TipoDeToken.punt_puntoYComa, ";");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_puntoYComa){
            match(TipoDeToken.punt_puntoYComa, ";");
        } else{
            throw new ExcepcionSintactica("tipo de asignación o ;", tokenActual);
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
            throw new ExcepcionSintactica("=, ++ o --", tokenActual);
        }
    }

    private void varLocal() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        tipo();
        match(TipoDeToken.id_metVar, "identificador de variable");
        varLocalFactorizada();
    }

    private void varLocalFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_asignacion){
            match(TipoDeToken.op_asignacion, "=");
            expresion();
        } else if(siguientes_varLocalFactorizada.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("= o ;", tokenActual);
        }
    }

    private void return_() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_return, "return");
        expresionOVacio();
    }

    private void expresionOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_expresion.contains(tokenActual.getTipoDeToken())){
            expresion();
        } else if(siguientes_expresionOVacio.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("una expresion o un ;", tokenActual);
        }
    }

    private void if_() throws IOException, ExcepcionLexica, ExcepcionSintactica{
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
        } else if(siguientes_ifFactorizado.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("el comienzo de una sentencia, un else o }", tokenActual);
        }
    }

    private void for_forEach() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_varLocalFactorizada.contains(tokenActual.getTipoDeToken()) || TipoDeToken.punt_puntoYComa == tokenActual.getTipoDeToken()){
            varLocalFactorizada();
            match(TipoDeToken.punt_puntoYComa, ";");
            expresion();
            match(TipoDeToken.punt_puntoYComa, ";");
            asignacion();
            match(TipoDeToken.punt_parentDer, ")");
            sentencia();
        } else if(TipoDeToken.punt_dosPuntos == tokenActual.getTipoDeToken()){
            match(TipoDeToken.punt_dosPuntos, ":");
            expresion();
            match(TipoDeToken.punt_parentDer, ")");
            sentencia();
        } else {
            throw new ExcepcionSintactica("un = un ; o un :", tokenActual);
        }
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
        } else if (siguientes_expresionRecursiva.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("un operador binario, un ;, o un , o un )", tokenActual);
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
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_mas){
            match(TipoDeToken.op_mas, "+");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menos){
            match(TipoDeToken.op_menos, "-");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_multiplicacion){
            match(TipoDeToken.op_multiplicacion, "*");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_division){
            match(TipoDeToken.op_division, "/");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_modulo){
            match(TipoDeToken.op_modulo, "%");
        } else {
            throw new ExcepcionSintactica("un operador binario", tokenActual);
        }
        
    }

    private void expresionUnaria() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_operadorUnario.contains(tokenActual.getTipoDeToken())){
            operadorUnario();
            operando();
        } else if(primeros_operando.contains(tokenActual.getTipoDeToken())){
            operando();
        } else{
            throw new ExcepcionSintactica("operador unario u operando", tokenActual);
        }
    }

    private void operadorUnario() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_mas){
            match(TipoDeToken.op_mas, "+");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menos){
            match(TipoDeToken.op_menos, "-");
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_not){
            match(TipoDeToken.op_not, "!");
        } else{
            throw new ExcepcionSintactica("operador unario", tokenActual);
        }
    }

    private void operando() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_literal.contains(tokenActual.getTipoDeToken())){
            literal();
        } else if(primeros_acceso.contains(tokenActual.getTipoDeToken())){
            acceso();
        } else{
            throw new ExcepcionSintactica("un literal o el comienzo de un acceso", tokenActual);
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
            throw new ExcepcionSintactica("un literal", tokenActual);
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
            throw new ExcepcionSintactica("el comienzo de un primario o casting", tokenActual);
        }
    }

    private void casting_expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.id_clase){
            match(TipoDeToken.id_clase, "identificador de clase");
            tipoParametricoOVacio();
            match(TipoDeToken.punt_parentDer, ")");
            primario_expresionParentizada();
            encadenado();
        } else if(primeros_expresionParentizada.contains(tokenActual.getTipoDeToken())){
            expresionParentizada();
            encadenado();
        } else{
            throw new ExcepcionSintactica("un identificador de clase o una expresion", tokenActual);
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
            throw new ExcepcionSintactica("un identificador de metodo o variable, this, o new", tokenActual);
        }
    }

    private void primario_expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_primario.contains(tokenActual.getTipoDeToken())){
            primario();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_parentIzq){
            match(TipoDeToken.punt_parentIzq, "(");
            expresionParentizada();
        } else{
            throw new ExcepcionSintactica("el comienzo de un primario", tokenActual);
        }
    }

    private void expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        expresion();
        match(TipoDeToken.punt_parentDer, ")");
    }

    private void accesoVar_accesoMetodo() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_argsActuales.contains(tokenActual.getTipoDeToken())){
            argsActuales();
        } else if (siguientes_accesoVar_accesoMetodo.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("alguno de los siguientes simbolos: {(, ., =, ++, --, ;, ||, && , ==, !=, < , > , <=, >= , +, -, *, /, %, ,, )}", tokenActual);
        }
    }

    private void accesoConstructor() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_new, "new");
        match(TipoDeToken.id_clase, "identificador de clase");
        tipoParametricoODiamante();
        argsActuales();
    }

    private void tipoParametricoODiamante() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(TipoDeToken.op_menor == tokenActual.getTipoDeToken()){
            match(TipoDeToken.op_menor, "<");
            tipoParametricoODiamanteFactorizado();
        } else{
            //vacio
        }
    }

    private void tipoParametricoODiamanteFactorizado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(TipoDeToken.op_mayor == tokenActual.getTipoDeToken()){
            match(TipoDeToken.op_mayor, ">");
        } else if(primeros_tipoParametrico.contains(tokenActual.getTipoDeToken())){
            tipoParametrico();
            listaTiposParametricos();
            match(TipoDeToken.op_mayor, ">");
        } else{
            throw new ExcepcionSintactica("un > o un identificador de clase", tokenActual);
        }
    }

    private void argsActuales() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.punt_parentIzq, "(");
        listaExpsOVacio();
        match(TipoDeToken.punt_parentDer, ")");
    }

    private void listaExpsOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_listaExps.contains(tokenActual.getTipoDeToken())){
            listaExps();
        } else if(siguientes_listaExpsOVacio.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("el comienzo de una expresion o )", tokenActual);
        }
    }

    private void listaExps() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        expresion();
        listaExpsFactorizada();
    }

    private void listaExpsFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_coma){
            match(TipoDeToken.punt_coma, ",");
            listaExps();
        } else if(siguientes_listaExpsFactorizada.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("un , o )", tokenActual);
        }
    }

    private void encadenado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_punto){
            varOMetodoEncadenado();
            encadenado();
        } else if (siguientes_encadenado.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("alguno de estos simbolos: {., =, ++, --, ;, ||, && , ==, !=, < , > , <=, >= , +, -, *, /, %, ,, )}", tokenActual);
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
        } else if (siguientes_varOMetodoEncadenado.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("un ( o alguno de estos símbolos: {., =, ++, --, ;, ||, && , ==, !=, < , > , <=, >= , +, -, *, /, %, ,, )}", tokenActual);
        }
    }













    






}
