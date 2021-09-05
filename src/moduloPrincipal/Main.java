package moduloPrincipal;
import java.io.*;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.ExcepcionLexica;
import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;
import manejadorDeArchivo.*;

public class Main {
    public static void main(String[] args){

        try{
            String filename = args[0];

            boolean sinErrores = true;
            boolean errorReciente = false;
            
            try {
                AnalizadorLexico analizadorLexico = new AnalizadorLexico(new GestorDeArchivo(filename));
                Token token = null;
                do{
                    try{
                        if(errorReciente){
                            errorReciente = false;
                            token = analizadorLexico.proximoTokenDespuesDeError(); //TODO: preguntar si esto esta bien.
                        } else{
                            token = analizadorLexico.proximoToken();
                        }
                        System.out.println("("+token.getTipoDeToken().toString()+", "+token.getLexema()+", "+token.getNroLinea()+")");
                    } catch (IOException e){
                        e.printStackTrace();
                    } catch (ExcepcionLexica e){
                        sinErrores = false;
                        errorReciente = true;
                        System.out.println(e.getMessage());
                    }
                } while(token == null || token.getTipoDeToken() != TipoDeToken.EOF); //TODO: preg si esta muy mal esa condicion ya que null da true solo la primera vez en determinados casos y nunca mas
                
                if(sinErrores){
                    System.out.println("\n[SinErrores]");
                }

            } catch (FileNotFoundException e){
                System.out.println("Error: no se encontró el archivo en la ruta especificada");
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error: falta el argumento con la ruta del archivo");
        }
    }
}