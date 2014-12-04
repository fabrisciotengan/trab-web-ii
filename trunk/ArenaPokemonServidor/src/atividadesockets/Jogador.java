/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesockets;

import java.util.List;

/**
 *
 * @author tierry
 */
public class Jogador {

    private int id;
    private String login;
    private String senha;
    private String pokemon;
    private String vida;
    private String direcao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void atacar(String direcao, int linha, int coluna, int id, int mapa[][], List<Jogador> lista) {
        int posicao = id;
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
                    if (mapa[posicaoLinha - i][posicaoColuna] < 0 || mapa[posicaoLinha - i][posicaoColuna] == 1) {
                        break;
                    } else {
                        //Atirou e acertou um alvo...
                        if (mapa[posicaoLinha - i][posicaoColuna] > 2) {
                            //Aqui fica o código onde o tiro acertou o alvo.
                            for (int j = 0; j < lista.size(); j++) {
                                Jogador get = lista.get(j);
                                if (get.getId() == mapa[posicaoLinha - i][posicaoColuna]) {
                                    get.setVida(Integer.toString(Integer.parseInt(get.getVida()) - 20));
                                    if (Integer.parseInt(get.getVida()) <= 20) {
                                        lista.remove(j);
                                        mapa[posicaoLinha - i][posicaoColuna] = 0;
                                    }
                                }

                            }
                            break;
                        }
                    }
                }
                break;

            case "2":
                //Aqui o tiro vai percorrer os 5 tails para baixo.
                for (int i = 1; i < 6; i++) {
                    //Atirou para cima e acertou a borda da matriz ou atirou numa parede...
                    if (mapa[posicaoLinha + i][posicaoColuna] > linha || mapa[posicaoLinha + i][posicaoColuna] == 1) {
                        break;
                    } else {
                        //Atirou e acertou um alvo...
                        if (mapa[posicaoLinha + i][posicaoColuna] > 2) {
                            //Aqui fica o código onde o tiro acertou o alvo.
                            for (int j = 0; j < lista.size(); j++) {
                                Jogador get = lista.get(j);
                                if (get.getId() == mapa[posicaoLinha + i][posicaoColuna]) {
                                    get.setVida(Integer.toString(Integer.parseInt(get.getVida()) - 20));
                                    if (Integer.parseInt(get.getVida()) <= 20) {
                                        lista.remove(j);
                                        mapa[posicaoLinha + i][posicaoColuna] = 0;
                                    }
                                }

                            }
                            break;
                        }
                    }
                }
                break;
            case "3":
                //Aqui o tiro vai percorrer os 5 tails para a direita.
                for (int i = 1; i < 6; i++) {
                    //Atirou para cima e acertou a borda da matriz ou atirou numa parede...
                    if (mapa[posicaoLinha][posicaoColuna + i] > coluna || mapa[posicaoLinha][posicaoColuna + i] == 1) {
                        break;
                    } else {
                        //Atirou e acertou um alvo...
                        if (mapa[posicaoLinha][posicaoColuna + i] > 2) {
                            //Aqui fica o código onde o tiro acertou o alvo.
                            for (int j = 0; j < lista.size(); j++) {
                                Jogador get = lista.get(j);
                                if (get.getId() == mapa[posicaoLinha][posicaoColuna + i]) {
                                    get.setVida(Integer.toString(Integer.parseInt(get.getVida()) - 20));
                                    if (Integer.parseInt(get.getVida()) <= 20) {
                                        lista.remove(j);
                                        mapa[posicaoLinha][posicaoColuna + i] = 0;
                                    }
                                }

                            }
                            break;
                        }
                    }
                }
                break;
            case "4":
                //Aqui o tiro vai percorrer os 5 tails para a esquerda.
                for (int i = 1; i < 6; i++) {
                    //Atirou para cima e acertou a borda da matriz ou atirou numa parede...
                    if (mapa[posicaoLinha][posicaoColuna - i] > coluna || mapa[posicaoLinha][posicaoColuna - i] == 1) {
                        break;
                    } else {
                        //Atirou e acertou um alvo...
                        if (mapa[posicaoLinha][posicaoColuna - i] > 2) {
                            //Aqui fica o código onde o tiro acertou o alvo.
                            for (int j = 0; j < lista.size(); j++) {
                                Jogador get = lista.get(j);
                                if (get.getId() == mapa[posicaoLinha][posicaoColuna - i]) {
                                    get.setVida(Integer.toString(Integer.parseInt(get.getVida()) - 20));
                                    if (Integer.parseInt(get.getVida()) <= 20) {
                                        lista.remove(j);
                                        mapa[posicaoLinha][posicaoColuna - i] = 0;
                                    }
                                }

                            }
                            break;
                        }
                    }
                }
                break;
            default:
                System.out.println("Problema ao atacar.");
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((direcao == null) ? 0 : direcao.hashCode());
        result = prime * result + id;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((pokemon == null) ? 0 : pokemon.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        result = prime * result + ((vida == null) ? 0 : vida.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Jogador other = (Jogador) obj;
        if (direcao == null) {
            if (other.direcao != null) {
                return false;
            }
        } else if (!direcao.equals(other.direcao)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        if (pokemon == null) {
            if (other.pokemon != null) {
                return false;
            }
        } else if (!pokemon.equals(other.pokemon)) {
            return false;
        }
        if (senha == null) {
            if (other.senha != null) {
                return false;
            }
        } else if (!senha.equals(other.senha)) {
            return false;
        }
        if (vida == null) {
            if (other.vida != null) {
                return false;
            }
        } else if (!vida.equals(other.vida)) {
            return false;
        }
        return true;
    }

}
