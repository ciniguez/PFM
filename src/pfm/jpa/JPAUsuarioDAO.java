package pfm.jpa;

import pfm.dao.UsuarioDAO;
import pfm.entidades.Usuario;

public class JPAUsuarioDAO extends JPAGenericDAO<Usuario, Integer> implements
		UsuarioDAO {

	public JPAUsuarioDAO() {
		super(Usuario.class);
	}
}
