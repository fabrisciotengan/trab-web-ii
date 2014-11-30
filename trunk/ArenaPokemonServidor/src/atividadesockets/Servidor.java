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
        Controle controle = new Controle();
        controle.iniciaMatriz(m);

        //Variáveis para testar o envio dos dados para a aplicação de mapa.
        LinkedList listaLigada = new LinkedList();
        Jogador personagem = new Jogador();
        Jogador personagem1 = new Jogador();
        personagem.setId("3");
        personagem.setPokemon("1");
        personagem.setVida("100");
        personagem.setDirecao("2");
        listaLigada.add(personagem);

        personagem1.setId("4");
        personagem1.setPokemon("3");
        personagem1.setVida("100");
        personagem1.setDirecao("4");
        listaLigada.add(personagem1);

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

                    boolean temInformacao = s.hasNextLine();
                    String buf = s.nextLine();

                    //Aqui será a parte onde o servidor irá verificar se é o cara do mapa que está requisitando informações.
                    if (temInformacao == true && "101".equals(buf)) {

                        try {
                            //Aqui é onde envia a string com a posição, id, vida... para o mapa.
                            saida = new PrintStream(cliente.getOutputStream());
                            saida.println(controle.imprimeJogadores(listaLigada, m));
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        //Parte do código que pega a string do cliente, separa e coloca em um objeto
                        if (buf.charAt(0) == '1' && buf.charAt(1) == ';' && temInformacao == true) {

                            if (controle.autentica(buf, personagem)) {
                                System.out.println(personagem.getLogin());
                                System.out.println(personagem.getSenha());
                                System.out.println(cliente.getInetAddress().getHostAddress() + " Logou-se ao jogo");

                                // Envia mensagem ao cliente, desejando boas vindas.   
                                try {
                                    saida = new PrintStream(cliente.getOutputStream());
                                    saida.println("Seja bem vindo ao servidor.");
                                } catch (IOException ex) {
                                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                // Aqui o servidor fica em um loop infinito recebendo informações do cliente até que ele envie "9999", que é onde sai do loop.
                                //Aqui dentro que tem que por os IF's para efetuar as ações de atacar... andar...
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

                    }

                    s.close();
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
