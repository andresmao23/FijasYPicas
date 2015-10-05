package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Mao
 */
public class PersonaRemota implements Runnable{
    
	int fijas = 0, picas = 0;
	
    private Thread thread;
    ServerSocket socket;
    PrintWriter salida;
    Socket comunicationSocket;
    
    GeneradorAleatorio g;
    private int [] generado;
    private int [] array;
    
    public PersonaRemota(){
    	
    	array = new int[4];
    	g = new GeneradorAleatorio();
    	generado = g.generarNumero();
    	
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
                
            	if (textoEntrada.equals("ingresarNumero")) {
                    ingresarNumero();
                }else if (textoEntrada.equals("decirEstado")) {
                    //decirEstado();
                }else if (textoEntrada.equals("despedirse")) {
                    //despedirse();
                }
            	
            	/*if (textoEntrada.equals("saludar")) {
                    //saludar();
                }else if (textoEntrada.equals("decirEstado")) {
                    //decirEstado();
                }else if (textoEntrada.equals("despedirse")) {
                    //despedirse();
                }*/
            }
        } catch (Exception e) {
            System.out.println("Error general " + e.getMessage());
        }
    }
    
    public boolean comparar(int [] array){    	
    	
    	for (int i = 0; i < generado.length; i++) {
			for (int j = 0; j < generado.length; j++) {
				if (i == j) {
					if (generado[i] == array[j]) {
						fijas++;
					}
				}else {
					if (generado[i] == array[j]) {
						picas++;
					}			
				}
			}
		}
    	
    	if (fijas == 4) {
			return true;
		}
    	
    	return false;
    }
    
    public void ingresarNumero(){
    	
    	salida.println("Numero aleatorio generado por el servidor: ");
		for (int i = 0; i < getGenerado().length; i++) {
			salida.printf("%-4d",getGenerado()[i]);
		}
    	
    	Scanner entrada = new Scanner(System.in);
    	
    	System.out.print("\nIngrese un número de 4 dígitos: ");
		int numero = entrada.nextInt();
		
		while (!(numero>=1000 && numero<=9999)) {
			System.out.print("\nIngrese un número de 4 dígitos: ");
			numero = entrada.nextInt();
		}
		salida.println("\nRespuesta del servidor (Numero original): "+numero);
		for (int i = 0; i < separarNumero(numero).length; i++) {
			salida.printf("%-4d",separarNumero(numero)[i]);
		}
		salida.println("\nComparacion:"+comparar(array));
		salida.println("Fijas:"+getFijas());
		salida.println("Picas:"+getPicas());
		
		
		
    }
    
    public int[] separarNumero(int numero){		
		array[3] = numero % 10;
		array[2] = numero % 100 / 10;
		array[1] = numero % 1000 / 100;
		array[0] = numero % 10000 / 1000;

		/*System.out.print("\nEl número ingresado está compuesto por los siguiente dígitos:\n");
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%-4d", array[i]);
		}*/
    	return array;
    }
    
    
    
    /*public void saludar(){
        salida.println("Saludando desde el servidor!!!");
    }
    
    public void decirEstado(){
        salida.println("Estoy contento en el servidor!!!");
    }
    
    public void despedirse(){
        salida.println("Despidiendome desde el servidor!!!");
    }*/
    
    
    public int getFijas() {
		return fijas;
	}
    
    public int getPicas() {
		return picas;
	}
    
    public int[] getGenerado() {
		return generado;
	}
    
    public static void main(String[] args){
        System.out.println("Servidor escuchando por el puerto 45454...");
        PersonaRemota pr = new PersonaRemota();
    }
    
}