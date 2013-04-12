package pfm.jpa;

import java.util.List;

import javax.persistence.Query;

import pfm.dao.UsuarioDAO;
import pfm.entidades.Rol;
import pfm.entidades.Usuario;

public class JPAUsuarioDAO extends JPAGenericDAO<Usuario, Integer> implements
		UsuarioDAO {

	public JPAUsuarioDAO() {
		super(Usuario.class);
	}

	@Override
	public List<Usuario> getEmpleado(Rol rol, boolean eliminado) {
		Query query = em.createNamedQuery("getEmpleado");
		query.setParameter("rol", rol);
		query.setParameter("eliminado", eliminado);
		@SuppressWarnings("unchecked")
		List<Usuario> resultado = query.getResultList();
		return resultado;

	}

}
