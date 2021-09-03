package manejadorDeArchivo;
import java.io.*;


public class GestorDeArchivo {

    private final char EOF = '\u001a';

    private BufferedReader reader;
    private boolean abierto;

    private String linea;
    private int char_pos;

    private int nroLineaActual;
    private boolean salteDeLinea;

    public GestorDeArchivo(String file) throws FileNotFoundException, IOException{
        char_pos = 0;
        nroLineaActual = 1;
        salteDeLinea = false;
        
        reader = new BufferedReader(new FileReader(file));
        abierto = true;
        linea = reader.readLine();
    }

    public int nroLinea(){
        if (salteDeLinea == true){
            salteDeLinea = false;
            return nroLineaActual - 1;
        }else{
            return nroLineaActual;
        }
    }

    public int nroColumna(){
        return char_pos + 1;
    }

    public char proximoCaracter() throws IOException{
        char character = EOF;
        salteDeLinea = false;
        
        if(linea != null){
            if(char_pos < linea.length()){
                character = linea.charAt(char_pos);
                char_pos++;
            }
            else{
                linea = reader.readLine();
                if(linea != null){
                    salteDeLinea = true;
                    nroLineaActual++;
                    char_pos = 0;
                    character = '\n';
                }
            }
        }

        if(abierto && esEOF(character)){
            abierto = false;
            reader.close();
        }
        
        return character;
    }

    public boolean esEOF(char c){
        return c == EOF;
    }
}
