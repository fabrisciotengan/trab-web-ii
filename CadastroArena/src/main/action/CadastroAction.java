package action;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

import bean.Usuario;

@Name("cadastroAction")
@Scope(ScopeType.SESSION)
public class CadastroAction {
	@Out
	@In
	private Usuario usuario;
	
	@DataModel
	private List<Usuario> usuarios;
	
	@In
	private EntityManager entityManager;
	
	
	
	public void salvar(){
		if(valido()){
			entityManager.persist(usuario);
			entityManager.flush();
		}
	}
	
	@Factory("usuarios")
	public List<Usuario> listar(){
		Session session = (Session) entityManager.getDelegate();
		Criteria criteria = session.createCriteria(Usuario.class);
		this.usuarios = criteria.list();
		return usuarios;
	}
	
	public boolean valido(){
		if(this.usuario.getLogin() == null){
			return false;
		}
		if(this.usuario.getPokemon() == null){
			return false;
		}
		if(this.usuario.getSenha() == null){
			return false;
		}
		
		return true;
	}
}
