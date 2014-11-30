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

        for (int i = 2; i < usuario.length(); i++) {
            if (controle == false) {
                if (usuario.charAt(i) == ';') {
                    controle = true;
                } else {
                    jogador.setLogin(jogador.getLogin() + usuario.charAt(i));
                }
            } else {
                jogador.setSenha(jogador.getSenha() + usuario.charAt(i));
            }
        }
        if ("thiago".equals(jogador.getLogin()) && "123456".equals(jogador.getSenha())) {
            return true;
        } else {
            return false;
        }
    }
    
    //Método que zera todas as posições da matriz e coloca os obstáculos randômicos na matriz
    public void iniciaMatriz(int mapa[][]) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mapa[i][j] = 0;
            }
        }
        mapa[3][2] = 3;
        mapa[4][1] = 4;
    }
    
    //Aqui há um for para percorrer a lista pegando os usuários e formando a string a ser enviada à aplicação mapa.
    public String imprimeJogadores(LinkedList lista, int mapa[][]) {
        String verJogadores = "101;";
        String posicao;
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mapa[i][j] != 0 && mapa[i][j] != 1 && mapa[i][j] != 2) {
                    posicao = Integer.toString(mapa[i][j]);
                    for (int k = 0; k < lista.size(); k++) {
                        Jogador jogador = (Jogador) lista.get(k);
                        if (jogador.getId() == null ? posicao == null : jogador.getId().equals(posicao)) {
                            verJogadores = verJogadores + jogador.getId() + ";" + jogador.getPokemon() + ";" + jogador.getVida() + ";" + posicao + ";" + jogador.getDirecao() + "#";
                        }
                    }
                }
            }
        }
        return verJogadores;
    }

}
