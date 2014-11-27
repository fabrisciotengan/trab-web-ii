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

        //Faz parte do teste também, aqui coloco dois usuários em alguma posição da matriz para poder validar a função de 
        m[3][2] = 3;
        m[4][1] = 4;

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
                    if (temInformacao == true && buf == "101") {
                        String verJogadores = "101;";
                        String posicao;

                        //Aqui há um for para percorrer a lista pegando os usuários e formando a string a ser enviada à aplicação mapa.
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (m[i][j] != 0 && m[i][j] != 1 && m[i][j] != 2) {
                                    posicao = Integer.toString(m[i][j]);
                                    for (int k = 0; k < listaLigada.size(); k++) {
                                        Jogador jogador = (Jogador) listaLigada.get(k);
                                        if (jogador.getId() == null ? posicao == null : jogador.getId().equals(posicao)) {
                                            verJogadores = verJogadores + jogador.getId() + ";" + jogador.getPokemon() + ";" + jogador.getVida() + ";" + posicao + ";" + jogador.getDirecao() + "#";
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
                        if (temInformacao == true){
                            
                            //Parte do código que pega a string do cliente, separa e coloca em um objeto
                            if(buf.charAt(0) == '1' && buf.charAt(1) == ';'){ 
                                personagem.setLogin("");
                                personagem.setSenha("");
                                boolean controle = false;
                                
                                for (int i = 2; i < buf.length(); i++) {
                                    if(controle == false){
                                        if(buf.charAt(i) == ';'){
                                            controle = true;
                                        }else{
                                            personagem.setLogin(personagem.getLogin()+buf.charAt(i));
                                        }
                                    }else{
                                        personagem.setSenha(personagem.getSenha()+buf.charAt(i));
                                    }                
                                }
                            }    
                            System.out.println(personagem.getLogin());
                            System.out.println(personagem.getSenha());
                            
                            /*System.out.println(cliente.getInetAddress().getHostAddress() + " Logou-se ao jogo");

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
                            }*/
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
