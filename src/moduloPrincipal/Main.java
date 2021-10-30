package moduloPrincipal;
import java.io.*;

import manejadorDeArchivo.*;
import analizadorLexico.AnalizadorLexico;
import analizadorLexico.ExcepcionLexica;
import analizadorSintactico.AnalizadorSintactico;
import analizadorSintactico.ExcepcionSintactica;
import tablaDeSimbolos.entidades.ExcepcionSemantica;
import tablaDeSimbolos.entidades.TablaSimbolos;

public class Main {
    public static void main(String[] args){
        
        try{
            String filename = args[0];

            try {
                AnalizadorLexico analizadorLexico = new AnalizadorLexico(new GestorDeArchivo(filename));

                // Creacion de la tabla de simbolos
                TablaSimbolos.getInstance();

                // Primer pasada: creación del sintáctico
                new AnalizadorSintactico(analizadorLexico); 

                // Segunda pasada (tiene dos partes)

                // Chequeo de declaraciones
                TablaSimbolos.getInstance().estaBienDeclarado();
                TablaSimbolos.getInstance().consolidar();

                // Chequeo de sentencias
                TablaSimbolos.getInstance().chequeoSentencias(); //TODO: con esto ya alcanza?

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