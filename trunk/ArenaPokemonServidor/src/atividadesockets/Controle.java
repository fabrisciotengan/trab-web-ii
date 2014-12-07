/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesockets;

import java.util.List;
import java.util.Random;

import Dao.JogadorDao;

/**
 *
 * @author tierry
 */
public class Controle {

    //Autentica os usuários
    public Jogador autentica(String usuario, List<Jogador> jogadores, int mapa[][], int linha, int coluna) {

        String comando;
        String login;
        String senha;

        String[] valores = usuario.split(";");
        comando = valores[0];
        login = valores[1];
        senha = valores[2];

//        JogadorDao jogadorDAO = new JogadorDao();
//        Jogador jogadorBD = jogadorDAO.buscar(login);
//        if (jogadorBD != null) {
//            if (jogadorBD.getSenha().equals(senha)) {
//                if (jogadores.contains(jogadorBD)) {
//                    jogadores.remove(jogadorBD);
//                    for (int i = 0; i < linha; i++) {
//                        for (int j = 0; j < coluna; j++) {
//                            if (mapa[i][j] == jogadorBD.getId()) {
//                                mapa[i][j] = 0;
//                            }
//                        }
//                    }
//                }
//                jogadores.add(jogadorBD);
//                return jogadorBD;
//            }
//        }
        
        if("balita".equals(login) && "123".equals(senha)){
            Jogador jogador = new Jogador();
            jogador.setId(12);
            jogador.setPokemon("Perebinha");
            
            if (jogadores.contains(jogador)) {
                    jogadores.remove(jogador);
                    for (int i = 0; i < linha; i++) {
                        for (int j = 0; j < coluna; j++) {
                            if (mapa[i][j] == jogador.getId()) {
                                mapa[i][j] = 0;
                            }
                        }
                    }
                }
            jogadores.add(jogador);
            return jogador;
        }
        return null;

    }

    //Método que zera todas as posições da matriz e coloca os obstáculos randômicos na matriz
    public void iniciaMatriz(int mapa[][], int linha, int coluna) {
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                mapa[i][j] = 0;
            }
        }
        Random rand = new Random();
        int li;
        int col;

        for (int i = 0; i < ((linha + coluna) / 5) + 1; i++) {
            while (true) {
                li = rand.nextInt(linha);
                col = rand.nextInt(coluna);
                if (mapa[li][col] == 0) {
                    mapa[li][col] = 1;
                    break;
                }
            }
            while (true) {
                li = rand.nextInt(linha);
                col = rand.nextInt(coluna);
                if (mapa[li][col] == 0) {
                    mapa[li][col] = 2;
                    break;
                }
            }
        }
    }

//Aqui há um for para percorrer a lista pegando os usuários e formando a string a ser enviada à aplicação mapa.
    public String verJogadores(List<Jogador> lista, int mapa[][], int linha, int coluna) {
        StringBuffer verJogadores = new StringBuffer();
        verJogadores.append("101;");
        int i = 0;

        for (Jogador jogador : lista) {
            verJogadores.append(jogador.getId()).append(";").append(jogador.getPokemon()).append(";").append(jogador.getVida()).append(";").append(jogador.getPontuacao()).append(";").append(jogador.getDirecao());
            if (i != lista.size() - 1) {
                verJogadores.append("#");
            }
            i++;
        }
        return verJogadores.toString();
    }

    //Método que mostra o mapa e todos os personagens nele.
    public String verMapa(int mapa[][], int linha, int coluna) {
        String l = Integer.toString(linha);
        String c = Integer.toString(coluna);

        String verMapa = "100;" + l + ";" + c;

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                verMapa = verMapa + ";" + Integer.toString(mapa[i][j]);
            }
        }
        return verMapa;
    }

    public void insereJogador(Jogador jogador, int mapa[][], int linha, int coluna) {
        Random rand = new Random();
        int li;
        int col;

        while (true) {
            li = rand.nextInt(linha);
            col = rand.nextInt(coluna);
            if (mapa[li][col] == 0) {
                mapa[li][col] = jogador.getId();
                break;
            }
        }
    }
}
