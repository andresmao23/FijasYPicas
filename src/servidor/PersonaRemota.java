package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Mao
 */
public class PersonaRemota implements Runnable{
    
    private Thread thread;
    ServerSocket socket;
    PrintWriter salida;
    Socket comunicationSocket;
    
    public PersonaRemota(){
        try {
            socket = new ServerSocket(45454);
            comunicationSocket = socket.accept();
            salida = new PrintWriter(comunicationSocket.getOutputStream(), true);
            thread = new Thread(this);
            thread.start();            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error " + e.getMessage());
        }
    }

    @Override
    public void run() {        
        String textoEntrada;
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(comunicationSocket.getInputStream()));
            while ((textoEntrada = entrada.readLine()) != null) {
                if (textoEntrada.equals("saludar")) {
                    saludar();
                }else if (textoEntrada.equals("decirEstado")) {
                    decirEstado();
                }else if (textoEntrada.equals("despedirse")) {
                    despedirse();
                }
            }
        } catch (Exception e) {
            System.out.println("Error general " + e.getMessage());
        }
    }
    
    public void saludar(){
        salida.println("Saludando desde el servidor!!!");
    }
    
    public void decirEstado(){
        salida.println("Estoy contento en el servidor!!!");
    }
    
    public void despedirse(){
        salida.println("Despidiendome desde el servidor!!!");
    }
    
    public static void main(String[] args){
        System.out.println("Servidor escuchando por el puerto 45454...");
        PersonaRemota pr = new PersonaRemota();
    }
    
}