package moduloPrincipal;
import java.io.*;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.ExcepcionLexica;
import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;
import manejadorDeArchivo.*;

public class Main {
    public static void main(String[] args){

      
        String filename = args[0];  //usar args[0] cuando lo compile
        try {
            AnalizadorLexico analizadorLexico = new AnalizadorLexico(new GestorDeArchivo(filename));
            Token token = analizadorLexico.proximoToken();
            //TODO IMPRIMIR EOF ES TOQUKENN
            while(token.getTipoDeToken() != TipoDeToken.EOF){
                System.out.println("("+token.getTipoDeToken().toString()+", "+token.getLexema()+", "+token.getNroLinea()+")");
                token = analizadorLexico.proximoToken();
            }
            System.out.println("("+token.getTipoDeToken().toString()+", "+token.getLexema()+", "+token.getNroLinea()+")");
            System.out.println("\n[SinErrores]");
            
        } catch (FileNotFoundException e){
            System.out.println("no se encontró el archivo para abrir");
        } catch (IOException e){
            e.printStackTrace();
        } catch (ExcepcionLexica e){
            System.out.println(e.getMessage());
        }

        
    }
}