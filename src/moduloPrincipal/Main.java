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
            
            try {
                AnalizadorLexico analizadorLexico = new AnalizadorLexico(new GestorDeArchivo(filename));
                Token token = new Token(TipoDeToken.DUMMY, "", 0);
                do{
                    try{
                        token = analizadorLexico.proximoToken();
                        System.out.println("("+token.getTipoDeToken().toString()+", "+token.getLexema()+", "+token.getNroLinea()+")");
                    } catch (IOException e){
                        e.printStackTrace();
                    } catch (ExcepcionLexica e){
                        sinErrores = false;
                        System.out.println(e.getMessage());
                        analizadorLexico.actualizarCaracterActual();
                    }
                } while(token.getTipoDeToken() != TipoDeToken.EOF);
                
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