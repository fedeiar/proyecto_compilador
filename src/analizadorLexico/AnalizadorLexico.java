package analizadorLexico;
import java.io.IOException;

import manejadorDeArchivo.*;

public class AnalizadorLexico {

    private GestorDeArchivo gestor;
    private TokenPalabrasReservadas token_pr;

    private String lexema;
    private int comienzoColLexema;
    private char caracterActual;

    public AnalizadorLexico(GestorDeArchivo gestor) throws IOException{
        this.gestor = gestor;
        token_pr = new TokenPalabrasReservadas();
        actualizarCaracterActual();
    }

    public Token proximoToken() throws IOException, ExcepcionLexica{
        lexema = "";
        return e0();
    }

    private void actualizarLexema(){
        lexema = lexema + caracterActual;
    }

    public void actualizarCaracterActual() throws IOException{
        caracterActual = gestor.proximoCaracter();
    }
    
    private Token e0() throws ExcepcionLexica, IOException{
        comienzoColLexema = gestor.nroColumna() - 1;
        
        if(Character.isWhitespace(caracterActual)){
            if(caracterActual == '\n'){
                comienzoColLexema = 1;
            }else{
                comienzoColLexema++;
            }
            actualizarCaracterActual();
            return e0();
        } else if(Character.isUpperCase(caracterActual)){
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        } else if(Character.isLowerCase(caracterActual)){
            actualizarLexema();
            actualizarCaracterActual();
            return e2();
        } else if(Character.isDigit(caracterActual)){
            actualizarLexema();
            actualizarCaracterActual();
            return e3();
        } else if(caracterActual == '\''){
            actualizarLexema();
            actualizarCaracterActual();
            return e4();
        } else if(caracterActual == '"'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5();
        } else if(caracterActual == '('){
            actualizarLexema();
            actualizarCaracterActual();
            return e6_1();
        } else if(caracterActual == ')'){
            actualizarLexema();
            actualizarCaracterActual();
            return e6_2();
        } else if(caracterActual == '{'){
            actualizarLexema();
            actualizarCaracterActual();
            return e6_3();
        } else if(caracterActual == '}'){
            actualizarLexema();
            actualizarCaracterActual();
            return e6_4();
        } else if(caracterActual == ';'){
            actualizarLexema();
            actualizarCaracterActual();
            return e6_5();
        } else if(caracterActual == ','){
            actualizarLexema();
            actualizarCaracterActual();
            return e6_6();
        } else if(caracterActual == '.'){
            actualizarLexema();
            actualizarCaracterActual();
            return e6_7();
        } else if(caracterActual == ':'){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TipoDeToken.punt_dosPuntos, lexema, gestor.nroLinea());
        } else if(caracterActual == '>'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_1();
        } else if(caracterActual == '<'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_2();
        } else if(caracterActual == '!'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_3();
        } else if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_4();
        } else if(caracterActual == '+'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_5();
        } else if(caracterActual == '-'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_6();
        } else if(caracterActual == '*'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_7();
        } else if(caracterActual == '&'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_8();
        } else if(caracterActual == '|'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_9();
        } else if(caracterActual == '%'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_10();
        } else if(caracterActual == '/'){
            actualizarLexema();
            actualizarCaracterActual();
            return e8();
        } else if(gestor.esEOF(caracterActual)){
            return e9();
        } else{
            actualizarLexema();
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), lexema+" no es un simbolo valido", comienzoColLexema, gestor.lineaCaracterAnterior());
        }
    }

    //Identificadores de clase

    private Token e1() throws ExcepcionLexica, IOException{
        if(Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_'){
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        }else{
            TipoDeToken tipoDeToken = token_pr.getTipoDeToken(lexema);
            if(tipoDeToken == null){
                return new Token(TipoDeToken.id_clase, lexema, gestor.nroLinea());
            } else{
                return new Token(tipoDeToken, lexema, gestor.nroLinea());
            }
        }
    }

    // Identificadores de metodos y variables

    private Token e2() throws ExcepcionLexica, IOException{
        if(Character.isLetter(caracterActual) || Character.isDigit(caracterActual) || caracterActual == '_'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2();
        }else{
            TipoDeToken tipoDeToken = token_pr.getTipoDeToken(lexema);
            if(tipoDeToken == null){
                return new Token(TipoDeToken.id_metVar, lexema, gestor.nroLinea());
            } else{
                return new Token(tipoDeToken, lexema, gestor.nroLinea());
            }
        }
    }

    // Literales enteros

    private Token e3() throws ExcepcionLexica, IOException{
        if(Character.isDigit(caracterActual)){
            actualizarLexema();
            actualizarCaracterActual();
            return e3();
        } else if(lexema.length() < 10){
            return new Token(TipoDeToken.lit_entero, lexema, gestor.nroLinea());
        } else{
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "el entero "+lexema+" contiene mas de 9 d??gitos", comienzoColLexema, gestor.lineaCaracterAnterior());
        }
    }

    // Literales caracter

    private Token e4() throws ExcepcionLexica, IOException{
        if(caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return e4_1();
        } else if (caracterActual == '\n' || caracterActual == '\'' ||gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "literal caracter invalido", comienzoColLexema, gestor.lineaCaracterAnterior());
        } else{
            actualizarLexema();
            actualizarCaracterActual();
            return e4_2();
        }
    }

    private Token e4_1() throws ExcepcionLexica, IOException{
        if(caracterActual == '\n' || gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "literal caracter invalido", comienzoColLexema, gestor.lineaCaracterAnterior());
        } else{
            actualizarLexema();
            actualizarCaracterActual();
            return e4_2();
        }
    }

    private Token e4_2() throws ExcepcionLexica, IOException{
        if(caracterActual == '\''){
            actualizarLexema();
            actualizarCaracterActual();
            return e4_3();
        } else{
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "literal caracter invalido", comienzoColLexema, gestor.lineaCaracterAnterior());
        }
    }

    private Token e4_3() throws ExcepcionLexica, IOException{
        return new Token(TipoDeToken.lit_caracter, lexema, gestor.nroLinea());
    }

    // Literales String

    private int comienzoString;
    private String primerLineaStringMultilinea;

    private Token eL5() throws ExcepcionLexica, IOException{
        comienzoString = gestor.nroLinea();
        primerLineaStringMultilinea = gestor.lineaCaracterAnterior();
        if(caracterActual == '\"'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_1();
        } else if(caracterActual == '\n' || gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, comienzoString, "Literal String sin cerrar apropiadamente", comienzoColLexema, primerLineaStringMultilinea);
        } else{
            return e5();
        }
    }

    private Token eL5_1() throws ExcepcionLexica, IOException{
        if(caracterActual == '\"'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_2();
        } else{
            return new Token(TipoDeToken.lit_string, lexema, comienzoString);
        }
    }

    private Token eL5_2() throws ExcepcionLexica, IOException{
        if(gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, comienzoString, "Literal String sin cerrar apropiadamente", comienzoColLexema, primerLineaStringMultilinea);
        } else if(caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_3();
        } else if(caracterActual == '\n'){
            lexema = lexema + '\\' + 'n';
            actualizarCaracterActual();
            return eL5_2();
        } else if(caracterActual == '\"'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_4();
        } else{
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_2();
        }
    }

    private Token eL5_3() throws ExcepcionLexica, IOException{
        if(gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, comienzoString, "Literal String sin cerrar apropiadamente", comienzoColLexema, primerLineaStringMultilinea);
        } else if(caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_3();
        } else if(caracterActual == '\n'){
            lexema = lexema + '\\' + 'n';
            actualizarCaracterActual();
            return eL5_2();
        } else{
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_2();
        }
    }

    private Token eL5_4() throws ExcepcionLexica, IOException{
        if(gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, comienzoString, "Literal String sin cerrar apropiadamente", comienzoColLexema, primerLineaStringMultilinea);
        } else if (caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_3();
        } else if (caracterActual == '\n'){
            lexema = lexema + '\\' + 'n';
            actualizarCaracterActual();
            return eL5_2();
        } else if(caracterActual == '\"'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_5();
        }
        else{
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_2();
        }
    }

    private Token eL5_5() throws ExcepcionLexica, IOException{
        if(gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, comienzoString, "Literal String sin cerrar apropiadamente", comienzoColLexema, primerLineaStringMultilinea);
        } else if (caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_3();
        } else if (caracterActual == '\n'){
            lexema = lexema + '\\' + 'n';
            actualizarCaracterActual();
            return eL5_2();
        } else if(caracterActual == '\"'){
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_6();
        }
        else{
            actualizarLexema();
            actualizarCaracterActual();
            return eL5_2();
        }
    }

    private Token eL5_6() throws ExcepcionLexica, IOException{
        return new Token(TipoDeToken.lit_string, lexema, comienzoString);
    }


    private Token e5() throws ExcepcionLexica, IOException{
        if(caracterActual == '\"'){
            actualizarLexema();
            actualizarCaracterActual();
            return e5_2();
        } else if(caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return e5_1();
        } else if(caracterActual == '\n' || gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "Literal String sin cerrar apropiadamente", comienzoColLexema, gestor.lineaCaracterAnterior());
        } else{
            actualizarLexema();
            actualizarCaracterActual();
            return e5();
        }
    }

    private Token e5_1() throws ExcepcionLexica, IOException{
        if(caracterActual == '\n' || gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "Literal String sin cerrar apropiadamente", comienzoColLexema, gestor.lineaCaracterAnterior());
        } else if (caracterActual == '\\'){
            actualizarLexema();
            actualizarCaracterActual();
            return e5_1();
        }else {
            actualizarLexema();
            actualizarCaracterActual();
            return e5();
        }
    }

    private Token e5_2() throws ExcepcionLexica, IOException{
        return new Token(TipoDeToken.lit_string, lexema, gestor.nroLinea());
    }

    // Puntuacion

    private Token e6_1() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.punt_parentIzq, lexema, gestor.nroLinea());
    }

    private Token e6_2() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.punt_parentDer, lexema, gestor.nroLinea());
    }

    private Token e6_3() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.punt_llaveIzq, lexema, gestor.nroLinea());
    }

    private Token e6_4() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.punt_llaveDer, lexema, gestor.nroLinea());
    }

    private Token e6_5() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.punt_puntoYComa, lexema, gestor.nroLinea());
    }

    private Token e6_6() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.punt_coma, lexema, gestor.nroLinea());
    }

    private Token e6_7() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.punt_punto, lexema, gestor.nroLinea());
    }

    // Operadores y asignacion (menos / que se fusiona con comentarios)

    private Token e7_1() throws IOException, ExcepcionLexica{
        if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_1_1();
        } else{
            return new Token(TipoDeToken.op_mayor, lexema, gestor.nroLinea());
        }
    }

    private Token e7_1_1() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_mayorOIgual, lexema, gestor.nroLinea());
    }

    private Token e7_2() throws IOException, ExcepcionLexica{
        if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_2_1();
        } else{
            return new Token(TipoDeToken.op_menor, lexema, gestor.nroLinea());
        }
    }

    private Token e7_2_1() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_menorOIgual, lexema, gestor.nroLinea());
    }

    private Token e7_3() throws IOException, ExcepcionLexica{
        if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_3_1();
        } else{
            return new Token(TipoDeToken.op_not, lexema, gestor.nroLinea());
        }
    }

    private Token e7_3_1() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_notIgual, lexema, gestor.nroLinea());
    }

    private Token e7_4() throws IOException, ExcepcionLexica{
        if(caracterActual == '='){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_4_1();
        } else{
            return new Token(TipoDeToken.op_asignacion, lexema, gestor.nroLinea());
        }
    }

    private Token e7_4_1() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_igualdad, lexema, gestor.nroLinea());
    }

    private Token e7_5() throws IOException, ExcepcionLexica{
        if(caracterActual == '+'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_5_1();
        } else{
            return new Token(TipoDeToken.op_mas, lexema, gestor.nroLinea());
        }
    }

    private Token e7_5_1() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_incremento, lexema, gestor.nroLinea());
    }

    private Token e7_6() throws IOException, ExcepcionLexica{
        if(caracterActual == '-'){
            actualizarLexema();
            actualizarCaracterActual();
            return e7_6_1();
        } else{
            return new Token(TipoDeToken.op_menos, lexema, gestor.nroLinea());
        }
    }

    private Token e7_6_1() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_decremento, lexema, gestor.nroLinea());
    }

    private Token e7_7() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_multiplicacion, lexema, gestor.nroLinea());
    }

    private Token e7_8() throws IOException, ExcepcionLexica{
        if(caracterActual == '&'){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TipoDeToken.op_and, lexema, gestor.nroLinea());
        } else{
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "operador incompleto", comienzoColLexema, gestor.lineaCaracterAnterior());
        }
    }

    private Token e7_9() throws IOException, ExcepcionLexica{
        if(caracterActual == '|'){
            actualizarLexema();
            actualizarCaracterActual();
            return new Token(TipoDeToken.op_or, lexema, gestor.nroLinea());
        } else{
            throw new ExcepcionLexica(lexema, gestor.nroLinea(), "operador incompleto", comienzoColLexema, gestor.lineaCaracterAnterior());
        }
        
    }

    private Token e7_10() throws IOException, ExcepcionLexica{
        return new Token(TipoDeToken.op_modulo, lexema, gestor.nroLinea());
    }
    
    // Comentarios (y el operador /)

    private int comienzoDeComentario;
    private String primerLineaComentario;

    private Token e8() throws ExcepcionLexica, IOException{
        comienzoDeComentario = gestor.nroLinea();
        primerLineaComentario = gestor.lineaCaracterAnterior();
        if (caracterActual == '/'){
            actualizarCaracterActual();
            return e8_1();
        } else if(caracterActual == '*'){
            actualizarCaracterActual();
            return e8_2();
        } else {
            return new Token(TipoDeToken.op_division, lexema, gestor.nroLinea());
        }
    }

    private Token e8_1() throws ExcepcionLexica, IOException{
        if (caracterActual == '\n'){
            lexema = "";
            actualizarCaracterActual();
            return e0();
        } else if(gestor.esEOF(caracterActual)){
            lexema = "";
            return e9();
        } else{
            actualizarCaracterActual();
            return e8_1();
        }
    }

    private Token e8_2() throws ExcepcionLexica, IOException{
        if (caracterActual == '*'){
            actualizarCaracterActual();
            return e8_3();
        } else if(gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica("", comienzoDeComentario, "comentario multi-linea sin cerrar", comienzoColLexema, primerLineaComentario);
        } else{
            actualizarCaracterActual();
            return e8_2();
        }
    }

    private Token e8_3() throws ExcepcionLexica, IOException{
        if (caracterActual == '*'){
            actualizarCaracterActual();
            return e8_3();
        } else if(caracterActual == '/'){
            lexema = "";
            actualizarCaracterActual();
            return e0();
        } else if(gestor.esEOF(caracterActual)){
            throw new ExcepcionLexica("", comienzoDeComentario, "comentario multi-linea sin cerrar", comienzoColLexema, primerLineaComentario);
        } else{
            actualizarCaracterActual();
            return e8_2();
        }
    }

    //EOF

    private Token e9(){
        return new Token(TipoDeToken.EOF, lexema, gestor.nroLinea());
    }

}
