package cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Mao
 */
public class PersonaProxy implements Runnable{

    private Thread thread;
    Socket socket;
    InputStream in;
    PrintWriter salida;
    int character;
    
    public PersonaProxy(){
        try {
            socket = new Socket("127.0.0.1", 45454);
            System.out.println("Conectando...");
            in = socket.getInputStream();
            salida = new PrintWriter(socket.getOutputStream(), true);
            thread = new Thread(this);
            thread.start();
        } catch (IOException io) {
            System.out.println("Error al conectarse al host remoto " + io.getMessage());
        } catch (Exception e) {
            System.out.println("Error general del obgeto proxy " + e.getMessage());
        }        
        
        if (socket != null && socket.isConnected()) {
            System.out.println("Conectado con el host remoto!!!");
        }
    }
    
    @Override
    public void run() {
        try {
            while ((character = in.read()) != -1) {                
                System.out.print((char) character);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void ingresarNumero(){
    	salida.println("ingresarNumero");
    }
    
    /*public void saludar(){
        salida.println("saludar");
    }
    
    public void decirEstado(){
        salida.println("decirEstado");
    }
    
    public void despedirse(){
        salida.println("despedirse");
    }*/
}