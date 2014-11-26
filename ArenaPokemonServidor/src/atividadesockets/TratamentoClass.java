/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesockets;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author POP
 */
public class TratamentoClass {

    TratamentoClass(Socket cliente) throws IOException {
        System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

        Scanner s = new Scanner(cliente.getInputStream());
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }

        s.close();
        cliente.close();
    }

}
