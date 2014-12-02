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
    
    public void minhaPosicao(){}
    
    public void andar(char direcao, int linha, int coluna, String id, int mapa[][] ){
        
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
        
        switch(direcao){
            case '1':
                if((posicaoColuna-1) > -1 && mapa[linha][coluna-1] < 3){
                    mapa[linha][coluna-1] = posicao;
                }
                break;
        }
    }
}