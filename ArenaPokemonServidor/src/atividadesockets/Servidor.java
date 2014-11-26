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
import java.util.LinkedList;
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

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                m[i][j] = 0;
            }
        }
        m[3][2] = 3;
        m[4][1] = 4;

        //Variáveis para testar o envio dos dados para a aplicação de mapa.
        LinkedList listaLigada = new LinkedList();
        ClienteClasse clientes = new ClienteClasse();
        ClienteClasse clientes1 = new ClienteClasse();
        clientes.setId("3");
        clientes.setPokemon("1");
        clientes.setVida("100");
        clientes.setDirecao("2");
        listaLigada.add(clientes);

        clientes1.setId("4");
        clientes1.setPokemon("3");
        clientes1.setVida("100");
        clientes1.setDirecao("4");
        listaLigada.add(clientes1);

        while (true) {
            Socket cliente = servidor.accept();

            Thread tratar = new Thread() {
                @Override
                public void run() {
                    System.out.println(cliente.getInetAddress().getHostAddress() + " Conectou ao servidor");
                    Scanner s = null;
                    PrintStream saida;

                    try {
                        s = new Scanner(cliente.getInputStream());
                    } catch (IOException ex) {
                        Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //Aqui será a parte onde o servidor irá verificar se é o cara do mapa que está requisitando informações.
                    if (s.hasNextLine() && "101".equals(s.nextLine())) {
                        String verJogadores = "101;";
                        String posicao;

                        //Aqui há um for para percorrer a lista pegando os usuários e formando a string a ser enviada à aplicação mapa.
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (m[i][j] != 0 && m[i][j] != 1 && m[i][j] != 2) {
                                    posicao = Integer.toString(m[i][j]);
                                    for (int k = 0; k < listaLigada.size(); k++) {
                                        ClienteClasse criente = (ClienteClasse) listaLigada.get(k);
                                        if (criente.getId() == null ? posicao == null : criente.getId().equals(posicao)) {
                                            verJogadores = verJogadores + criente.getId() + ";" + criente.getPokemon() + ";" + criente.getVida() + ";" + posicao + ";" + criente.getDirecao() + "#";
                                        }
                                    }
                                }
                            }
                        }
                        
                        //Aqui é onde envia a string com a posição, id, vida... para o mapa.
                        try {
                            saida = new PrintStream(cliente.getOutputStream());
                            saida.println(verJogadores);
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                        //Aqui é onde realiza a autenticação do usuário e inicia suas ações.
                        if (s.hasNextLine() && "1;thiago;123456".equals(s.nextLine())) {

                            System.out.println(cliente.getInetAddress().getHostAddress() + " Logou-se ao jogo");

                            // Envia mensagem ao cliente, desejando boas vindas.   
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
