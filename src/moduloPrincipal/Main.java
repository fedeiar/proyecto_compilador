package moduloPrincipal;
import java.io.*;

import analizadorLexico.AnalizadorLexico;
import analizadorLexico.ExcepcionLexica;
import analizadorSintactico.AnalizadorSintactico;
import analizadorSintactico.ExcepcionSintactica;
import manejadorDeArchivo.*;

public class Main {
    public static void main(String[] args){
        
        
        try{
            String filename = args[0];

            try {
                AnalizadorLexico analizadorLexico = new AnalizadorLexico(new GestorDeArchivo(filename));
                AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico);

                System.out.println("Compilacion exitosa\n\n[SinErrores]");

            } catch (FileNotFoundException e){
                System.out.println("Error: no se encontro el archivo en la ruta especificada");
            } catch (IOException e){
                e.printStackTrace();
            } catch(ExcepcionLexica e){
                System.out.println(e.getMessage());
            } catch(ExcepcionSintactica e){
                System.out.println(e.getMessage());
            }


        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error: falta el argumento con la ruta del archivo");
        }

        //TODO: HAY QUE LIMPIAR LA TABLA DE SIMBOLOS AL FINAL DEL MAIN! ya que es un singleton.
    }
}