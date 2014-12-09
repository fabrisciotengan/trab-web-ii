/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesocketscliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author POP
 */
public class Cliente {

    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args)
            throws UnknownHostException, IOException, InterruptedException {
        // TODO code application logic here
        Socket cliente = new Socket("192.168.2.100", 12345);

        Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(cliente.getOutputStream());

        //saida.println("1;balita;123");
        saida.println("101");
//         Imprime a mensagem recebida do servidor...
        Scanner s = null;
        s = new Scanner(cliente.getInputStream());
        String teste = s.nextLine();
        System.out.println("Servidor: " + teste);


        while (teclado.hasNextLine()) {
            String palavra = teclado.nextLine();
            saida.println(palavra);
            if ("9999".equals(palavra)) {
                System.out.println("Desconectado do servidor.");

                break;
            }
        }
        saida.close();
        teclado.close();
        cliente.close();
    }
}
