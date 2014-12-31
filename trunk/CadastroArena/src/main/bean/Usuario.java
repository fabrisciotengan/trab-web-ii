package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Entity
@Scope(ScopeType.CONVERSATION)
@Name("usuario")
@AutoCreate
public class Usuario {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;
	    private String login;
	    private String senha;
	    private String pokemon;
	    private String vida;
	    private String direcao;
	    @Column(columnDefinition = "float default 0")
	    private int score;
	    
	    

	    public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

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
	
	
}
