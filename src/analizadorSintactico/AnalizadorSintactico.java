package analizadorSintactico;

import analizadorLexico.*;
import tablaDeSimbolos.*;
import tablaDeSimbolos.nodosAST.*;
import tablaDeSimbolos.tipos.*;
import java.io.IOException;
import java.util.ArrayList;
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

    private void TipoParametricoOVacioNoAnidado() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(TipoDeToken.op_menor == tokenActual.getTipoDeToken()){
            match(TipoDeToken.op_menor, "<");
            match(TipoDeToken.id_clase, "identificador de clase");
            listaTiposParametricosNoAnidados();
            match(TipoDeToken.op_mayor, ">");
        } else{
            //vacio
        }
    }

    private void listaTiposParametricosNoAnidados() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(TipoDeToken.punt_coma == tokenActual.getTipoDeToken()){
            match(TipoDeToken.punt_coma, ",");
            match(TipoDeToken.id_clase, "identificador de clase");
            listaTiposParametricosNoAnidados();
        } else{
            //vacio
        }

    }

    private void clase() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        match(TipoDeToken.pr_class, "class");
        Token tokenIdClase = tokenActual;
        match(TipoDeToken.id_clase, "identificador de clase");
        Clase clase = new Clase(tokenIdClase);
        TablaSimbolos.claseActual = clase;
        TablaSimbolos.getInstance().insertarClase(tokenIdClase.getLexema(), clase);

        TipoParametricoOVacioNoAnidado(); //TODO: completar para logro genericidad

        Token tokenIdClaseAncestro = herencia();
        clase.set_idClaseAncestro(tokenIdClaseAncestro);

        match(TipoDeToken.punt_llaveIzq, "{");
        listaMiembros();
        match(TipoDeToken.punt_llaveDer, "}");
    }

    private Token herencia() throws IOException, ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_extends){
            match(TipoDeToken.pr_extends, "extends");
            Token tokenIdClase = tokenActual;
            match(TipoDeToken.id_clase, "identificador de clase");
            
            TipoParametricoOVacioNoAnidado(); //TODO: completar para logro genericidad

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
            TablaSimbolos.claseActual.insertarConstructor(constructor); 

            NodoBloque nodoBloque = bloque(); 
            constructor.insertarBloque(nodoBloque);
        } else if(primeros_tipoParametricoOVacio.contains(tokenActual.getTipoDeToken()) || primeros_listaDecAtrs.contains(tokenActual.getTipoDeToken())){
            tipoParametricoOVacio(); //TODO: completar para logro genericidad
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
        TablaSimbolos.unidadActual = metodo;
        argsFormales();
        TablaSimbolos.claseActual.insertarMetodo(metodo);

        NodoBloque nodoBloque = bloque();
        metodo.insertarBloque(nodoBloque);
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

            tipoParametricoOVacio(); //TODO: completar para logro genericidad

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
        TablaSimbolos.claseActual.insertarAtributo(tokenIdVar.getLexema(), atributo);
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
        TablaSimbolos.unidadActual.insertarParametro(tokenIdVar.getLexema(), parametro);
    }

    //aca comienzan las sentencias

    private NodoBloque bloque() throws IOException, ExcepcionLexica, ExcepcionSintactica{   
        match(TipoDeToken.punt_llaveIzq, "{");
        NodoBloque nodoBloque = new NodoBloque();
        listaSentencias(nodoBloque);
        match(TipoDeToken.punt_llaveDer, "}");
        return nodoBloque;
    }

    private void listaSentencias(NodoBloque nodoBloque) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_sentencia.contains(tokenActual.getTipoDeToken())){
            NodoSentencia nodoSentencia = sentencia();
            nodoBloque.insertarSentencia(nodoSentencia);
            listaSentencias(nodoBloque);
        } else if(siguientes_listaSentencias.contains(tokenActual.getTipoDeToken())){
            //vacio
        } else{
            throw new ExcepcionSintactica("una sentencia o }", tokenActual);
        }
    }

    private NodoSentencia sentencia() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_puntoYComa){
            match(TipoDeToken.punt_puntoYComa, ";");
            return null; //TODO: está bien devovler nulo y controlarlo en insertarSentencia?
        } else if(primeros_acceso.contains(tokenActual.getTipoDeToken())){
            NodoAcceso nodoAcceso = acceso();
            return asignacion_llamada(nodoAcceso);
        } else if(primeros_varLocal.contains(tokenActual.getTipoDeToken())){
            NodoSentencia nodoSentencia = varLocal();
            match(TipoDeToken.punt_puntoYComa, ";");
            return nodoSentencia;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_return){
            NodoSentencia nodoSentencia = return_();
            match(TipoDeToken.punt_puntoYComa, ";");
            return nodoSentencia;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_if){
            return if_();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_for){
            match(TipoDeToken.pr_for, "for");
            match(TipoDeToken.punt_parentIzq, "(");
            Tipo tipo = tipo();
            Token tokenIdVar = tokenActual;
            match(TipoDeToken.id_metVar, "identificador de variable");
            NodoVarLocal nodoVarLocal = new NodoVarLocal(tokenIdVar, tipo);
            return for_forEach(nodoVarLocal);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_llaveIzq){
            return bloque();
        } else{
            throw new ExcepcionSintactica("un ; o el comienzo de una sentencia", tokenActual);
        }
    }

    private NodoSentencia asignacion_llamada(NodoAcceso nodoAcceso) throws IOException, ExcepcionLexica, ExcepcionSintactica{ //TODO: SEGUIR DESDE ACA
        if(primeros_tipoDeAsignacion.contains(tokenActual.getTipoDeToken())){
            NodoAsignacion nodoAsignacion = tipoDeAsignacion(nodoAcceso);
            match(TipoDeToken.punt_puntoYComa, ";");
            return nodoAsignacion;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_puntoYComa){
            match(TipoDeToken.punt_puntoYComa, ";");
            return new NodoLlamada(nodoAcceso);
        } else{
            throw new ExcepcionSintactica("tipo de asignación o ;", tokenActual);
        }
    }

    private NodoAsignacion asignacion() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        NodoAcceso nodoAcceso = acceso();
        return tipoDeAsignacion(nodoAcceso);
    }

    private NodoAsignacion tipoDeAsignacion(NodoAcceso nodoAcceso) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_asignacion){
            match(TipoDeToken.op_asignacion, "=");
            NodoExpresion nodoExpresion = expresion();
            return new NodoAsignacionExpresion(nodoAcceso, nodoExpresion);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_incremento){
            Token tokenIncremento = tokenActual;
            match(TipoDeToken.op_incremento, "++");
            return new NodoAsignacionIncremento(nodoAcceso, tokenIncremento);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_decremento){
            Token tokenDecremento = tokenActual;
            match(TipoDeToken.op_decremento, "--");
            return new NodoAsignacionDecremento(nodoAcceso, tokenDecremento);
        } else{
            throw new ExcepcionSintactica("=, ++ o --", tokenActual);
        }
    }

    private NodoVarLocal varLocal() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        Tipo tipo = tipo();
        Token tokenIdVar = tokenActual;
        NodoVarLocal nodoVarLocal = new NodoVarLocal(tokenIdVar, tipo);
        match(TipoDeToken.id_metVar, "identificador de variable");
        NodoExpresion nodoExpresion = varLocalFactorizada();
        nodoVarLocal.insertarExpresion(nodoExpresion);
        return nodoVarLocal;
    }

    private NodoExpresion varLocalFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_asignacion){
            match(TipoDeToken.op_asignacion, "=");
            return expresion();
        } else if(siguientes_varLocalFactorizada.contains(tokenActual.getTipoDeToken())){
            //vacio
            return null;
        } else{
            throw new ExcepcionSintactica("= o ;", tokenActual);
        }
    }

    private NodoReturn return_() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_return, "return");
        NodoReturn NodoReturn = new NodoReturn();
        NodoExpresion nodoExpresion = expresionOVacio();
        NodoReturn.insertarExpresion(nodoExpresion);
        return NodoReturn;
    }

    private NodoExpresion expresionOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_expresion.contains(tokenActual.getTipoDeToken())){
            return expresion();
        } else if(siguientes_expresionOVacio.contains(tokenActual.getTipoDeToken())){
            //vacio
            return null;
        } else{
            throw new ExcepcionSintactica("una expresion o un ;", tokenActual);
        }
    }

    private NodoIf if_() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_if, "if");
        match(TipoDeToken.punt_parentIzq, "(");
        NodoExpresion nodoExpresion = expresion();
        match(TipoDeToken.punt_parentDer, ")");
        NodoSentencia nodoSentenciaIf = sentencia();
        NodoIf nodoIf = new NodoIf(nodoExpresion, nodoSentenciaIf);
        NodoSentencia nodoSentenciaElse = ifFactorizado();
        nodoIf.insertarSentenciaElse(nodoSentenciaElse);
        return nodoIf;
    }

    private NodoSentencia ifFactorizado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_else){
            match(TipoDeToken.pr_else, "else");
            return sentencia();
        } else if(siguientes_ifFactorizado.contains(tokenActual.getTipoDeToken())){
            //vacio
            return null;
        } else{
            throw new ExcepcionSintactica("el comienzo de una sentencia, un else o }", tokenActual);
        }
    }

    private NodoFor for_forEach(NodoVarLocal nodoVarLocal) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_varLocalFactorizada.contains(tokenActual.getTipoDeToken()) || TipoDeToken.punt_puntoYComa == tokenActual.getTipoDeToken()){
            NodoExpresion nodoExpresionVarLocal = varLocalFactorizada();
            nodoVarLocal.insertarExpresion(nodoExpresionVarLocal);
            match(TipoDeToken.punt_puntoYComa, ";");
            NodoExpresion nodoExpresionFor = expresion();
            match(TipoDeToken.punt_puntoYComa, ";");
            NodoAsignacion nodoAsignacion = asignacion();
            match(TipoDeToken.punt_parentDer, ")");
            NodoSentencia nodoSentencia = sentencia();
            return new NodoFor(nodoVarLocal, nodoExpresionFor, nodoAsignacion, nodoSentencia);
        } else if(TipoDeToken.punt_dosPuntos == tokenActual.getTipoDeToken()){ //TODO: el else es para el forEach. Completar Genericidad primero si se quiere este logro en la etapa 4.
            match(TipoDeToken.punt_dosPuntos, ":");
            expresion();
            match(TipoDeToken.punt_parentDer, ")");
            sentencia();
            return null; //TODO: en realidad devuelve el nodoForEach, pero necesitamos genericidad.
        } else {
            throw new ExcepcionSintactica("un = un ; o un :", tokenActual);
        }
    }

    private NodoExpresion expresion() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        NodoExpresion nodoExpresionLadoIzq = expresionUnaria();
        return expresionRecursiva(nodoExpresionLadoIzq);
    }

    private NodoExpresion expresionRecursiva(NodoExpresion nodoExpresionLadoIzq) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_operadorBinario.contains(tokenActual.getTipoDeToken())){
            Token tokenOperadorBinario = operadorBinario();
            NodoExpresion nodoExpresionLadoDer = expresionUnaria();
            NodoExpresionBinaria nodoExpresionBinaria = new NodoExpresionBinaria(tokenOperadorBinario, nodoExpresionLadoIzq, nodoExpresionLadoDer);
            NodoExpresion nodoExpresion = expresionRecursiva(nodoExpresionBinaria);
            return nodoExpresion;
        } else if (siguientes_expresionRecursiva.contains(tokenActual.getTipoDeToken())){
            //vacio
            return nodoExpresionLadoIzq;
        } else{
            throw new ExcepcionSintactica("un operador binario, un ;, o un , o un )", tokenActual);
        }
    }

    private Token operadorBinario() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_or){
            Token tokenOr = tokenActual;
            match(TipoDeToken.op_or, "||");
            return tokenOr;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_and){
            Token tokenAnd = tokenActual;
            match(TipoDeToken.op_and, "&&");
            return tokenAnd;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_igualdad){
            Token tokenIgualdad = tokenActual;
            match(TipoDeToken.op_igualdad, "==");
            return tokenIgualdad;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_notIgual){
            Token tokenNotIgual = tokenActual;
            match(TipoDeToken.op_notIgual, "!=");
            return tokenNotIgual;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menor){
            Token tokenMenor = tokenActual;
            match(TipoDeToken.op_menor, "<");
            return tokenMenor;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_mayor){
            Token tokenMayor = tokenActual;
            match(TipoDeToken.op_mayor, ">");
            return tokenMayor;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menorOIgual){
            Token tokenMenorOIgual = tokenActual;
            match(TipoDeToken.op_menorOIgual, "<=");
            return tokenMenorOIgual;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_mayorOIgual){
            Token tokenMayorOIgual = tokenActual;
            match(TipoDeToken.op_mayorOIgual, ">=");
            return tokenMayorOIgual;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_mas){
            Token tokenMas = tokenActual;
            match(TipoDeToken.op_mas, "+");
            return tokenMas;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menos){
            Token tokenMenos = tokenActual;
            match(TipoDeToken.op_menos, "-");
            return tokenMenos;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_multiplicacion){
            Token tokenMultiplicacion = tokenActual;
            match(TipoDeToken.op_multiplicacion, "*");
            return tokenMultiplicacion;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_division){
            Token tokenDivision = tokenActual;
            match(TipoDeToken.op_division, "/");
            return tokenDivision;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_modulo){
            Token tokenModulo = tokenActual;
            match(TipoDeToken.op_modulo, "%");
            return tokenModulo;
        } else{
            throw new ExcepcionSintactica("un operador binario", tokenActual);
        }
        
    }

    private NodoExpresion expresionUnaria() throws IOException, ExcepcionLexica, ExcepcionSintactica{ 
        if(primeros_operadorUnario.contains(tokenActual.getTipoDeToken())){
            Token tokenOperador = operadorUnario();
            NodoOperando nodoOperando = operando();
            return new NodoExpresionUnaria(tokenOperador, nodoOperando);
        } else if(primeros_operando.contains(tokenActual.getTipoDeToken())){
            return operando();
        } else{
            throw new ExcepcionSintactica("operador unario u operando", tokenActual);
        }
    }

    private Token operadorUnario() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.op_mas){
            Token tokenMas = tokenActual;
            match(TipoDeToken.op_mas, "+");
            return tokenMas;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_menos){
            Token tokenMenos = tokenActual;
            match(TipoDeToken.op_menos, "-");
            return tokenMenos;
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.op_not){
            Token tokenNot = tokenActual;
            match(TipoDeToken.op_not, "!");
            return tokenNot;
        } else{
            throw new ExcepcionSintactica("operador unario", tokenActual);
        }
    }

    private NodoOperando operando() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_literal.contains(tokenActual.getTipoDeToken())){
            return literal();
        } else if(primeros_acceso.contains(tokenActual.getTipoDeToken())){
            return acceso();
        } else{
            throw new ExcepcionSintactica("un literal o el comienzo de un acceso", tokenActual);
        }
    }

    private NodoOperando literal() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.pr_null){
            Token tokenNull = tokenActual;
            match(TipoDeToken.pr_null, "null");
            return new NodoNull(tokenNull);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_true){
            Token tokenTrue = tokenActual;
            match(TipoDeToken.pr_true, "true");
            return new NodoTrue(tokenTrue);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_false){
            Token tokenFalse = tokenActual;
            match(TipoDeToken.pr_false, "false");
            return new NodoFalse(tokenFalse);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.lit_entero){
            Token tokenLitEntero = tokenActual;
            match(TipoDeToken.lit_entero, "literal entero");
            return new NodoEntero(tokenLitEntero);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.lit_caracter){
            Token tokenLitCaracter = tokenActual;
            match(TipoDeToken.lit_caracter, "literal caracter");
            return new NodoCaracter(tokenLitCaracter);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.lit_string){
            Token tokenLitString = tokenActual;
            match(TipoDeToken.lit_string, "literal String");
            return new NodoString(tokenLitString);
        } else{
            throw new ExcepcionSintactica("un literal", tokenActual);
        }
    }

    private NodoAcceso acceso() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_parentIzq){
            match(TipoDeToken.punt_parentIzq, "(");
            return casting_expresionParentizada();
        } else if(primeros_primario.contains(tokenActual.getTipoDeToken())){
            NodoPrimario nodoPrimario = primario();
            NodoEncadenado nodoEncadenado = encadenado();
            nodoPrimario.insertarNodoEncadenado(nodoEncadenado);
            return nodoPrimario;
        } else{
            throw new ExcepcionSintactica("el comienzo de un primario o casting", tokenActual);
        }
    }

    private NodoAcceso casting_expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.id_clase){
            Token tokenIdClaseCasting = tokenActual;
            match(TipoDeToken.id_clase, "identificador de clase");
            
            tipoParametricoOVacio(); //TODO: para hacer algo con esto, necesitamos genericidad de etapa 3.

            match(TipoDeToken.punt_parentDer, ")");
            NodoPrimario nodoPrimario = primario_expresionParentizada();
            NodoEncadenado nodoEncadenado = encadenado();
            nodoPrimario.insertarNodoEncadenado(nodoEncadenado);
            NodoPrimarioCasting nodoPrimarioCasting = new NodoPrimarioCasting(tokenIdClaseCasting, nodoPrimario);
            return nodoPrimarioCasting;
        } else if(primeros_expresionParentizada.contains(tokenActual.getTipoDeToken())){
            NodoExpresionParentizada nodoExpresionParentizada = expresionParentizada();
            NodoEncadenado nodoEncadenado = encadenado();
            nodoExpresionParentizada.insertarNodoEncadenado(nodoEncadenado);
            return nodoExpresionParentizada;
        } else{
            throw new ExcepcionSintactica("un identificador de clase o una expresion", tokenActual);
        }
    }

    private NodoPrimario primario() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.id_metVar){
            Token tokenIdMetVar = tokenActual;
            match(TipoDeToken.id_metVar, "identificador de metodo o variable");
            return accesoVar_accesoMetodo(tokenIdMetVar);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_this){
            Token tokenThis = tokenActual;
            match(TipoDeToken.pr_this, "this");
            return new NodoThis(tokenThis);
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.pr_new){
            return accesoConstructor();
        } else{
            throw new ExcepcionSintactica("un identificador de metodo o variable, this, o new", tokenActual);
        }
    }

    private NodoPrimario primario_expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_primario.contains(tokenActual.getTipoDeToken())){
            return primario();
        } else if(tokenActual.getTipoDeToken() == TipoDeToken.punt_parentIzq){
            match(TipoDeToken.punt_parentIzq, "(");
            return expresionParentizada();
        } else{
            throw new ExcepcionSintactica("el comienzo de un primario", tokenActual);
        }
    }

    private NodoExpresionParentizada expresionParentizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        NodoExpresion nodoExpresion = expresion();
        match(TipoDeToken.punt_parentDer, ")");
        return new NodoExpresionParentizada(nodoExpresion);
    }

    private NodoPrimario accesoVar_accesoMetodo(Token tokenIdMetVar) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_argsActuales.contains(tokenActual.getTipoDeToken())){
            List<NodoExpresion> listaParametrosActuales = argsActuales();
            return new NodoAccesoMetodo(tokenIdMetVar, listaParametrosActuales);
        } else if (siguientes_accesoVar_accesoMetodo.contains(tokenActual.getTipoDeToken())){
            //vacio
            return new NodoAccesoVar(tokenIdMetVar);
        } else{
            throw new ExcepcionSintactica("alguno de los siguientes simbolos: {(, ., =, ++, --, ;, ||, && , ==, !=, < , > , <=, >= , +, -, *, /, %, ,, )}", tokenActual);
        }
    }

    private NodoAccesoConstructor accesoConstructor() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.pr_new, "new");
        Token tokenIdClase = tokenActual;
        match(TipoDeToken.id_clase, "identificador de clase");

        tipoParametricoODiamante(); //TODO: se necesita genericidad etapa 3 para hacer algo con esto

        List<NodoExpresion> listaParametrosActuales = argsActuales();
        return new NodoAccesoConstructor(tokenIdClase, listaParametrosActuales);
    }

    private void tipoParametricoODiamante() throws IOException, ExcepcionLexica, ExcepcionSintactica{ //TODO: se necesita genericidad etapa 3 para hacer algo con esto
        if(TipoDeToken.op_menor == tokenActual.getTipoDeToken()){
            match(TipoDeToken.op_menor, "<");
            tipoParametricoODiamanteFactorizado();
        } else{
            //vacio
        }
    }

    private void tipoParametricoODiamanteFactorizado() throws IOException, ExcepcionLexica, ExcepcionSintactica{ //TODO: se necesita genericidad etapa 3 para hacer algo con esto
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

    private List<NodoExpresion> argsActuales() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.punt_parentIzq, "(");
        List<NodoExpresion> listaParametrosActuales = listaExpsOVacio();
        match(TipoDeToken.punt_parentDer, ")");
        return listaParametrosActuales;
    }

    private List<NodoExpresion> listaExpsOVacio() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_listaExps.contains(tokenActual.getTipoDeToken())){
            return listaExps();
        } else if(siguientes_listaExpsOVacio.contains(tokenActual.getTipoDeToken())){
            //vacio
            return new ArrayList<NodoExpresion>();
        } else{
            throw new ExcepcionSintactica("el comienzo de una expresion o )", tokenActual);
        }
    }

    private List<NodoExpresion> listaExps() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        NodoExpresion nodoExpresion = expresion();
        List<NodoExpresion> listaParametrosActuales = listaExpsFactorizada();
        listaParametrosActuales.add(0, nodoExpresion); //TODO: preguntar si está bien hacer el addFirst de esta manera.
        return listaParametrosActuales;
    }

    private List<NodoExpresion> listaExpsFactorizada() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_coma){
            match(TipoDeToken.punt_coma, ",");
            return listaExps();
        } else if(siguientes_listaExpsFactorizada.contains(tokenActual.getTipoDeToken())){
            //vacio
            return new ArrayList<NodoExpresion>();
        } else{
            throw new ExcepcionSintactica("un , o )", tokenActual);
        }
    }

    private NodoEncadenado encadenado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(tokenActual.getTipoDeToken() == TipoDeToken.punt_punto){
            NodoEncadenado nodoEncadenado = varOMetodoEncadenado();
            NodoEncadenado nodoEncadenadoSiguiente = encadenado();
            nodoEncadenado.insertarNodoEncadenado(nodoEncadenadoSiguiente);
            return nodoEncadenado;
        } else if (siguientes_encadenado.contains(tokenActual.getTipoDeToken())){
            //vacio
            return null;
        } else{
            throw new ExcepcionSintactica("alguno de estos simbolos: {., =, ++, --, ;, ||, && , ==, !=, < , > , <=, >= , +, -, *, /, %, ,, )}", tokenActual);
        }
    }

    private NodoEncadenado varOMetodoEncadenado() throws IOException, ExcepcionLexica, ExcepcionSintactica{
        match(TipoDeToken.punt_punto, ".");
        Token tokenIdMetVar = tokenActual;
        match(TipoDeToken.id_metVar, "identificador de metodo o variable");
        return varOMetodoEncadenadoFactorizado(tokenIdMetVar);
    }

    private NodoEncadenado varOMetodoEncadenadoFactorizado(Token tokenIdMetVar) throws IOException, ExcepcionLexica, ExcepcionSintactica{
        if(primeros_argsActuales.contains(tokenActual.getTipoDeToken())){
            List<NodoExpresion> listaParametrosActuales = argsActuales();
            NodoAccesoMetodoEncadenado nodoAccesoMetodoEncadenado = new NodoAccesoMetodoEncadenado(tokenIdMetVar, listaParametrosActuales);
            return nodoAccesoMetodoEncadenado;
        } else if (siguientes_varOMetodoEncadenado.contains(tokenActual.getTipoDeToken())){
            //vacio
            return new NodoAccesoVarEncadenada(tokenIdMetVar);
        } else{
            throw new ExcepcionSintactica("un ( o alguno de estos símbolos: {., =, ++, --, ;, ||, && , ==, !=, < , > , <=, >= , +, -, *, /, %, ,, )}", tokenActual);
        }
    }













    






}
