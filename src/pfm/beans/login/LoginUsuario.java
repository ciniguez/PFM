package pfm.beans.login;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import pfm.dao.UsuarioDAO;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "loginUsuario")
public class LoginUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO usuarioDAO;
	private String nombre;
	private String contrasenia;

	//GETTERS AND SETTERS
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	//OPERACIONES
	public String procesar() {
		List<Usuario> usuarios = JPADAOFactory.getFactory().getUsuarioDAO()
				.find(new String[] { "username", "password" }, 
						new String[] { this.getNombre(), this.getContrasenia()}, null, 0, 0);
		if (usuarios.size() > 0) {
			for (Usuario usuario : usuarios) {
				System.out.println(usuario.getApellidos());
			}
		} else {
			System.out.println("no paso");
		}

		return "";
	}

}
