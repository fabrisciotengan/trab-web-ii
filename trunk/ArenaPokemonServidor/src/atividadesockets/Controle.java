/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividadesockets;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Dao.JogadorDao;

/**
 *
 * @author tierry
 */
public class Controle {

    //Autentica os usuários
    public Jogador autentica(String usuario, List<Jogador> jogadores) {

        boolean controle = false;

        String comando;
        String login;
        String senha;

        String[] valores = usuario.split(";");
        comando = valores[0];
        login = valores[1];
        senha = valores[2];
        
        JogadorDao jogadorDAO = new JogadorDao();
    	Jogador jogadorBD = jogadorDAO.buscar(login);
    	if(jogadorBD != null){
    		if(jogadorBD.getSenha().equals(senha)){
    			jogadores.add(jogadorBD);
    			return jogadorBD;
    		}
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
        mapa[3][2] = 3;
        mapa[4][1] = 4;
    }

    //Aqui há um for para percorrer a lista pegando os usuários e formando a string a ser enviada à aplicação mapa.
    public String verJogadores(List<Jogador> lista, int mapa[][], int linha, int coluna) {
        StringBuffer verJogadores = new StringBuffer();
        verJogadores.append("101;");
        int i = 0;
        
        for (Jogador jogador : lista) {
        	verJogadores.append(jogador.getId()).append(";").append(jogador.getPokemon()).append(";").append(jogador.getVida()).append(";").append(jogador.getDirecao());
            if(i != lista.size() - 1){
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
    public void insereJogador(Jogador jogador, int mapa[][], int linha, int coluna){
    	Random rand = new Random();
    	
    	linha = rand.nextInt(linha);
    	rand = new Random();
    	coluna = rand.nextInt(coluna);
    }
}
