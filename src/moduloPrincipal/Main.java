package moduloPrincipal;
import java.io.*;

import manejadorDeArchivo.*;
import analizadorLexico.AnalizadorLexico;
import analizadorLexico.ExcepcionLexica;
import analizadorSintactico.AnalizadorSintactico;
import analizadorSintactico.ExcepcionSintactica;
import tablaDeSimbolos.ExcepcionSemantica;
import tablaDeSimbolos.TablaSimbolos;

public class Main {
    public static void main(String[] args){
        
        try{
            String filename = args[0];

            try {
                AnalizadorLexico analizadorLexico = new AnalizadorLexico(new GestorDeArchivo(filename));

                //creacion de la tabla de simbolos
                TablaSimbolos.getInstance();

                //Cuando se crea el Sintactico, se hace la primer pasada
                AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico); 

                //Segunda pasada
                TablaSimbolos.getInstance().estaBienDeclarado();
                TablaSimbolos.getInstance().consolidar();

                System.out.println("Compilacion exitosa\n\n[SinErrores]");

            } catch (FileNotFoundException e){
                System.out.println("Error: no se encontro el archivo en la ruta especificada");
            } catch (IOException e){
                e.printStackTrace();
            } catch(ExcepcionLexica e){
                System.out.println(e.getMessage());
            } catch(ExcepcionSintactica e){
                System.out.println(e.getMessage());
            } catch(ExcepcionSemantica e){
                System.out.println(e.getMessage());
            }


        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Error: falta el argumento con la ruta del archivo");
        }

        TablaSimbolos.reiniciar();
    }
}