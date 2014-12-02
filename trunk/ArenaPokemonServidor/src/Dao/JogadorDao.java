package Dao;

import java.sql.Connection;   
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import java.util.Vector;

import atividadesockets.Jogador;

public class JogadorDao {
	
	private final String URL = "jdbc:mysql://localhost:3306",  
	         NOME = "root", SENHA = "1234";  
	  
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

	
	
	public Vector<Jogador> buscar(String login) {  
	      conectar();  
	      Vector<Jogador> resultados = new Vector<Jogador>();  
	      ResultSet rs;  
	      try {  
	         rs = comando.executeQuery("SELECT * FROM jogador WHERE login = ;" + login); 
	         
	         while (rs.next()) {  
	        	 
	            Jogador temp = new Jogador();  
	            // pega todos os atributos da pessoa  
	            temp.setId(rs.getInt("id"));  
	            temp.setLogin(rs.getString("login"));  
	            temp.setSenha(rs.getString("senha"));  
	            temp.setPokemon(rs.getString("pokemon"));  
	            
	            resultados.add(temp);  
	         }  
	         return resultados;  
	      } catch (SQLException e) {   
	         return null;  
	      }  
	  
	   } 
	
	   private void fechar() {  
		      try {  
		         comando.close();  
		         con.close();  
		         System.out.println("Conexão Fechada");  
		      } catch (SQLException e) {  
		         System.out.println("Erro ao fechar conexão");  
		      }  
		   } 	
} 


	


