/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author POP
 */
public class Servidor {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    //Matriz do nosso joguinho =]
    static int m[][] = new int[5][5];
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Porta 12345 aberta!");

        while (true) {
            Socket cliente = servidor.accept();

            Thread tratar = new Thread() {
                @Override
                public void run() {
                    System.out.println(cliente.getInetAddress().getHostAddress() + " Conectou ao servidor");
                    Scanner s = null;

                    try {
                        s = new Scanner(cliente.getInputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Aqui será a parte onde o servidor irá verificar se é o cara do mapa que está requisitando informações.
                    /*if(s.hasNextLine() && "101".equals(s.nextLine())){
                    }*/
                    
                    //Aqui é onde realiza a autenticação do usuário e inicia suas ações.
                    if (s.hasNextLine() && "1;thiago;123456".equals(s.nextLine())) {
                        
                        System.out.println(cliente.getInetAddress().getHostAddress() + " Logou-se ao jogo");
                        
                        // Envia mensagem ao cliente, desejando boas vindas.   
                        PrintStream saida;
                        try {
                            saida = new PrintStream(cliente.getOutputStream());
                            saida.println("Seja bem vindo ao servidor.");
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }                       
                        
                        // Aqui o servidor fica em um loop infinito recebendo informações do cliente até que ele envie "9999", que é onde sai do loop.
                        while (s.hasNextLine()) {
                            String codigo = s.nextLine();
                            System.out.println(codigo);
                            if ("9999".equals(codigo)) {
                                System.out.println(cliente.getInetAddress().getHostAddress() + " Desconectou-se");
                                break;
                            }
                        }
                    } else {
                        System.out.println(cliente.getInetAddress().getHostAddress() + " Falha ao conecatar-se ao jogo, login ou senha incorreta.");
                        s.close();
                        try {
                            cliente.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    try {
                        cliente.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            };
            tratar.start();
        }
    }
}
