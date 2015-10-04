package cliente;

/**
 *
 * @author Mao
 */
public class ClienteMain {
    public static void main(String[] args){
        PersonaProxy pp = new PersonaProxy();
        pp.saludar();
        pp.decirEstado();
        pp.despedirse();
    }
}
