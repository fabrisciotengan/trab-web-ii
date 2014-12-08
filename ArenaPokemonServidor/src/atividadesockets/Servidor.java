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
import java.util.ArrayList;
import java.util.List;
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
        final List<Jogador> listaJogadores = new ArrayList<>();
        final Jogador personagem = new Jogador();
        Jogador personagem1 = new Jogador();

        personagem.setId(3);
        personagem.setPokemon("Pika-Pika");
        listaJogadores.add(personagem);
        controle.insereJogador(personagem, m, linha, coluna);

        personagem1.setId(4);
        personagem1.setPokemon("Charizard");
        listaJogadores.add(personagem1);
        controle.insereJogador(personagem1, m, linha, coluna);

        while (true) {
            final Socket cliente = servidor.accept();
            System.out.println("Há: " + Thread.activeCount() + " threads ativas até momento.");

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
                    String entrada = s.nextLine();

                    //Ver mapa
                    if (temInformacao == true && "100".equals(entrada)) {
                        try {
                            //Aqui é onde envia a string com a posição, id, vida... para o mapa.
                            saida = new PrintStream(cliente.getOutputStream());
                            saida.println(controle.verMapa(m, linha, coluna));
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    //Aqui será a parte onde o servidor irá verificar se é o cara do mapa que está requisitando informações.
                    if (temInformacao == true && "101".equals(entrada)) {

                        try {
                            //Aqui é onde envia a string com a posição, id, vida... para o mapa.
                            saida = new PrintStream(cliente.getOutputStream());
                            saida.println(controle.verJogadores(listaJogadores, m, linha, coluna));
                        } catch (IOException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        //Parte do código que pega a string do cliente, separa e coloca em um objeto
                        //Autenticação
                        if (entrada.charAt(0) == '1' && entrada.charAt(1) == ';' && temInformacao == true) {
                            Jogador jogador = controle.autentica(entrada, listaJogadores, m, linha, coluna);
                            if (jogador == null ? false : true) {
                                System.out.println(cliente.getInetAddress().getHostAddress() + " Logou-se ao jogo");

                                //coloca o jogador em uma posição random na matriz
                                controle.insereJogador(jogador, m, linha, coluna);

                                // Envia mensagem ao cliente, desejando boas vindas.   
                                try {
                                    saida = new PrintStream(cliente.getOutputStream());
                                    saida.println("Você foi conectado com o id: " + jogador.getId());
                                } catch (IOException ex) {
                                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                /* Aqui o servidor fica em um loop infinito recebendo informações do cliente até que ele envie "9999", que é onde sai do loop.
                                 * Aqui dentro que tem que por os IF's para efetuar as ações de atacar... andar..
                                 */
                                while (s.hasNextLine() && cliente.isConnected()) {
                                    String codigo = s.nextLine();
                                    System.out.println(codigo);

                                    //Se o usuário enviar o código 9999 ele desconecta do servidor.
                                    if ("9999".equals(codigo)) {
                                        System.out.println(cliente.getInetAddress().getHostAddress() + " Desconectou-se");
                                        break;
                                    } else {
                                        //Direação
                                        if (codigo.charAt(0) == '1' && codigo.charAt(1) == '0' && codigo.charAt(2) == ';' && (codigo.charAt(3) == '1' || codigo.charAt(3) == '2' || codigo.charAt(3) == '3' || codigo.charAt(3) == '4')) {
                                            switch (codigo.charAt(3)) {
                                                //Muda a direação para cima
                                                case '1':
                                                    jogador.setDirecao("1");
                                                    break;
                                                //Muda a direação para baixo
                                                case '2':
                                                    jogador.setDirecao("2");
                                                    break;
                                                //Muda a direação para direita
                                                case '3':
                                                    jogador.setDirecao("3");
                                                    break;
                                                //Muda a direação para esquerda
                                                case '4':
                                                    jogador.setDirecao("4");
                                                    break;
                                                default:
                                                    System.out.println("B.Ó no código do cliente xômano.");
                                            }
                                        } else {
                                            //Andar
                                            if (codigo.charAt(0) == '1' && codigo.charAt(1) == '1') {
                                                jogador.andar(jogador.getDirecao(), linha, coluna, jogador.getId(), m);
                                            } else {
                                                //Atacar
                                                if (codigo.charAt(0) == '1' && codigo.charAt(1) == '2') {
                                                    jogador.atacar(jogador, linha, coluna, m, listaJogadores);
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
                            //Aqui remove o jogador do mapa.
                            jogador.removeMapa(jogador, linha, coluna, m, listaJogadores);
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
