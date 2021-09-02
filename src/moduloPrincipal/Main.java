package moduloPrincipal;
import java.io.*;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.ExcepcionLexica;
import analizadorLexico.TipoDeToken;
import analizadorLexico.Token;
import manejadorDeArchivo.*;

public class Main {
    public static void main(String[] args){

        //TODO: preguntar si para capturar multiples errores, estaría bien poner el try-catch dentro un while.
        String filename = args[0];  //usar args[0] cuando lo compile
        try {
            AnalizadorLexico analizadorLexico = new AnalizadorLexico(new GestorDeArchivo(filename));
            Token token = analizadorLexico.proximoToken();
            while(token.getTipoDeToken() != TipoDeToken.EOF){
                System.out.println("("+token.getTipoDeToken().toString()+", "+token.getLexema()+", "+token.getNroLinea()+")");
                token = analizadorLexico.proximoToken();
            }
            System.out.println("\n[SinErrores]");
            
        } catch (FileNotFoundException e){
            System.out.println("no se encontró el archivo para abrir");
        } catch (IOException e){
            e.printStackTrace();
        } catch (ExcepcionLexica e){
            System.out.println(e.getMessage());
        }

        /*
        try{
            GestorDeArchivo gestor = new GestorDeArchivo("file.txt");
            char c = gestor.proximoCaracter();
            int i = 0;
            while(!gestor.esEOF(c)){
                System.out.print(c);
                c = gestor.proximoCaracter();
                i++;
            }
            System.out.println("\n\ncantidad de caracteres: "+i);
        } catch(IOException e){
            e.printStackTrace();
        }
        */
    }
}