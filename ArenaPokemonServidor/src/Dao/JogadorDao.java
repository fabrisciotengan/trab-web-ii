package Dao;

import java.sql.Connection;   
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import atividadesockets.Jogador;

public class JogadorDao {
	
	private final String URL = "jdbc:mysql://localhost/arena",  
	         NOME = "root", SENHA = "root";  
	  
	   private Connection con;  
	   private Statement comando;  


	
	private void conectar() {  
	      try {  
	         con = ConFactory.conexao(URL, NOME, SENHA, ConFactory.MYSQL);  
	         comando = con.createStatement();  
	         System.out.println("Conectado!");  
	      } catch (ClassNotFoundException e) {  
	         System.out.print("Erro ao carregar o driver");  
	      } catch (SQLException e) {  
	         System.out.print("Erro ao conectar");  
	      }  
	   }

	
	
	public Jogador buscar(String login) {  
	      conectar();  
	      Jogador jogador = new Jogador();  
	      ResultSet rs;  
	         try {
				rs = comando.executeQuery("SELECT * FROM usuario WHERE login = '" + login + "';");
	         
	         while (rs.next()) {  
	        	 
	            // pega todos os atributos da pessoa  
	            jogador.setId(rs.getInt("id"));  
	            jogador.setLogin(rs.getString("login"));  
	            jogador.setPokemon(rs.getString("pokemon"));  
	            jogador.setSenha(rs.getString("senha"));  
	            
	         }  
	         } catch (SQLException e) {
	        	 // TODO Auto-generated catch block
	        	 e.printStackTrace();
	         } 
	         return jogador;  
	  
	   } 
	
	   private void fechar() {  
		      try {  
		         comando.close();  
		         con.close();  
		         System.out.println("Conex�o Fechada");  
		      } catch (SQLException e) {  
		         System.out.println("Erro ao fechar conex�o");  
		      }  
		   } 	
} 


	


