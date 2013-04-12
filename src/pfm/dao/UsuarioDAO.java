package pfm.dao;

import java.util.List;

import pfm.entidades.Rol;
import pfm.entidades.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario, Integer> {

	public List<Usuario> getEmpleado(Rol rol, boolean eliminado);
}
