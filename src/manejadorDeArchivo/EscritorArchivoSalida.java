package manejadorDeArchivo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EscritorArchivoSalida {
    

    public static void crearArchivo(String nombreArchivoSalida, List<String> listaInstrucciones) throws IOException{
        FileWriter writer = new FileWriter(nombreArchivoSalida);
        for(String instruccion : listaInstrucciones){
            writer.write(instruccion+"\n");
        }
        writer.close();
    }
}
