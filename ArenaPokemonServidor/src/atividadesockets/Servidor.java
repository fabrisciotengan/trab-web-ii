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
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        final int linha = 5;
        final int coluna = 5;
        final int m[][] = new int[linha][coluna];
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Porta 12345 aberta!");
        final Controle controle = new Controle();
        controle.iniciaMatriz(m, linha, coluna);

        //Variáveis para testar o envio dos dados para a aplicação de mapa.
        final LinkedList listaLigada = new LinkedList();
        final Jogador personagem = new Jogador();
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
            final Socket cliente = servidor.accept();

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

                    //Ver mapa
                    if (temInformacao == true && "100".equals(buf)) {
                        try {
                            //Aqui é onde envia a string com a posição, id, vida... para o mapa.
                            saida = new PrintStream(cliente.getOutputStream());
                            saida.println(controle.verMapa(m, linha, coluna));
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    //Aqui será a parte onde o servidor irá verificar se é o cara do mapa que está requisitando informações.
                    if (temInformacao == true && "101".equals(buf)) {

                        try {
                            //Aqui é onde envia a string com a posição, id, vida... para o mapa.
                            saida = new PrintStream(cliente.getOutputStream());
                            saida.println(controle.verJogadores(listaLigada, m, linha, coluna));
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

                                /* Aqui o servidor fica em um loop infinito recebendo informações do cliente até que ele envie "9999", que é onde sai do loop.
                                 * Aqui dentro que tem que por os IF's para efetuar as ações de atacar... andar..
                                 */
                                while (s.hasNextLine()) {
                                    String codigo = s.nextLine();
                                    System.out.println(codigo);
                                    //Se o usuário enviar o código 9999 ele desconecta do servidor.
                                    if ("9999".equals(codigo)) {
                                        System.out.println(cliente.getInetAddress().getHostAddress() + " Desconectou-se");
                                        break;
                                    } else {
                                        //Direação
                                        if (codigo.charAt(0) == '1' && codigo.charAt(1) == '0' && codigo.charAt(2) == ';') {
                                            switch (codigo.charAt(3)) {
                                                //Muda a direação para cima
                                                case '1':
                                                    personagem.setDirecao("1");
                                                    break;
                                                //Muda a direação para baixo
                                                case '2':
                                                    personagem.setDirecao("2");
                                                    break;
                                                //Muda a direação para direita
                                                case '3':
                                                    personagem.setDirecao("3");
                                                    break;
                                                //Muda a direação para esquerda
                                                case '4':
                                                    personagem.setDirecao("4");
                                                    break;  
                                                default:
                                                    System.out.println("B.Ó no código do cliente xômano.");
                                            }
                                        } else {
                                            //Atacar
                                            if (codigo.charAt(0) == '1' && codigo.charAt(1) == '1') {
                                                
                                            } else {
                                                //Andar
                                                if (codigo.charAt(0) == '1' && codigo.charAt(1) == '2') {
                                                    personagem.andar(personagem.getDirecao(), linha, coluna, personagem.getId(), m);
                                                }
                                            }
                                        }
                                    }
                                }

                            } else {
                                try {
                                    saida = new PrintStream(cliente.getOutputStream());
                                    saida.println(cliente.getInetAddress().getHostAddress() + " Falha ao conectar-se ao jogo, login ou senha incorreta.");
                                    System.out.println(cliente.getInetAddress().getHostAddress() + " Falha ao conectar-se ao jogo, login ou senha incorreta.");
                                    s.close();
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
