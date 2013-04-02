package pfm.dao;

import pfm.entidades.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario, Integer> {

	public boolean autenticarUsuario(String nombre, String contrasenia);
}
