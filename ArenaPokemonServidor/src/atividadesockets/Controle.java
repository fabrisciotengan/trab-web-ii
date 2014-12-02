/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesockets;

import java.util.LinkedList;

/**
 *
 * @author tierry
 */
public class Controle {

    //Autentica os usuários
    public boolean autentica(String usuario, Jogador jogador) {

        jogador.setLogin("");
        jogador.setSenha("");
        boolean controle = false;

        String comando;
        String login;
        String senha;

        String[] valores = usuario.split(";");
        comando = valores[0];
        login = valores[1];
        senha = valores[2];

        jogador.setLogin(login);
        jogador.setSenha(senha);
        if ("thiago".equals(jogador.getLogin()) && "123456".equals(jogador.getSenha())) {
            return true;
        } else {
            return false;
        }
    }

    //Método que zera todas as posições da matriz e coloca os obstáculos randômicos na matriz
    public void iniciaMatriz(int mapa[][], int linha, int coluna) {
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                mapa[i][j] = 0;
            }
        }
        mapa[3][2] = 3;
        mapa[4][1] = 4;
    }

    //Aqui há um for para percorrer a lista pegando os usuários e formando a string a ser enviada à aplicação mapa.
    public String verJogadores(LinkedList lista, int mapa[][], int linha, int coluna) {
        String verJogadores = "101;";
        String posicao;

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                if (mapa[i][j] != 0 && mapa[i][j] != 1 && mapa[i][j] != 2) {
                    posicao = Integer.toString(mapa[i][j]);
                    for (Object lista1 : lista) {
                        Jogador jogador = (Jogador) lista1;
                        if (jogador.getId() == null ? posicao == null : jogador.getId().equals(posicao)) {
                            verJogadores = verJogadores + jogador.getId() + ";" + jogador.getPokemon() + ";" + jogador.getVida() + ";" + jogador.getDirecao() + "#";
                        }
                    }
                }
            }
        }
        return verJogadores;
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

}
