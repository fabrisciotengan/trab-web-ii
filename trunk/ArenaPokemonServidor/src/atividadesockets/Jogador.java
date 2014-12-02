/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesockets;

import javax.xml.bind.ParseConversionEvent;

/**
 *
 * @author tierry
 */
public class Jogador {

    private String id;
    private String login;
    private String senha;
    private String pokemon;
    private String vida;
    private String direcao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(String pokemon) {
        this.pokemon = pokemon;
    }

    public String getVida() {
        return vida;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void andar(String direcao, int linha, int coluna, String id, int mapa[][]) {

        int posicao = Integer.parseInt(id);
        int posicaoLinha = 0;
        int posicaoColuna = 0;

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                if (mapa[i][j] == posicao) {
                    posicaoLinha = i;
                    posicaoColuna = j;
                }
            }
        }

        switch (direcao) {
            case "1":
                if ((posicaoLinha - 1) > -1 && mapa[posicaoLinha - 1][posicaoColuna] == 0) {
                    mapa[posicaoLinha][posicaoColuna] = 0;
                    mapa[posicaoLinha - 1][posicaoColuna] = posicao;
                }
                break;
            case "2":
                if ((posicaoLinha + 1) < linha && mapa[posicaoLinha + 1][posicaoColuna] == 0) {
                    mapa[posicaoLinha][posicaoColuna] = 0;
                    mapa[posicaoLinha + 1][posicaoColuna] = posicao;
                }
                break;
            case "3":
                if ((posicaoColuna + 1) < coluna && mapa[posicaoLinha][posicaoColuna + 1] == 0) {
                    mapa[posicaoLinha][posicaoColuna] = 0;
                    mapa[posicaoLinha][posicaoColuna + 1] = posicao;
                }
                break;
            case "4":
                if ((posicaoColuna - 1) > -1 && mapa[posicaoLinha][posicaoColuna - 1] == 0) {
                    mapa[posicaoLinha][posicaoColuna] = 0;
                    mapa[posicaoLinha][posicaoColuna - 1] = posicao;
                }
                break;
            default:
                System.out.println("Erro, comando de direção incorreto.");
        }

    }

    public void atacar(String direcao, int linha, int coluna, String id, int mapa[][]) {
        int posicao = Integer.parseInt(id);
        int posicaoLinha = 0;
        int posicaoColuna = 0;

        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                if (mapa[i][j] == posicao) {
                    posicaoLinha = i;
                    posicaoColuna = j;
                }
            }
        }

        switch (direcao) {
            case "1":
                //Aqui o tiro vai percorrer os 5 tails acima.
                for (int i = 1; i < 6; i++) {
                    //Atirou para cima e acertou a borda da matriz ou atirou numa parede...
                    if(mapa[posicaoLinha-i][posicaoColuna] < 0 || mapa[posicaoLinha-i][posicaoColuna] == 1 ){
                        break;
                    }else{
                        //Atirou e acertou um alvo...
                        if(mapa[posicaoLinha-1][posicaoColuna] > 2){
                            //Aqui fica o código onde o tiro acertou o alvo.
                            
                            break;
                        }
                    }
                    
                }
                break;
            case "2":

                break;
            case "3":
 
                break;
            case "4":

                break;
            default:
                System.out.println("Problema ao atacar.");
        }

    }
}
