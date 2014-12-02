package Teste;

import java.util.Vector;

import atividadesockets.Jogador;
import Dao.JogadorDao;

public class Teste {
	
	public static void main(String[] args){
		
		JogadorDao obj = new JogadorDao();
		 Vector<Jogador> resultado = obj.buscar("bruno");
		 
		 System.out.println(((Jogador) resultado.get(0)).getId());
		
		
		
		
	}

}
