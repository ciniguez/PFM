package pfm.beans.login;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pfm.dao.UsuarioDAO;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "loginUsuario")
@RequestScoped
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

	public String logout() {
		System.out.println("usuario salio");
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		return "logout";
	}

	//OPERACIONES
	public String procesar() {
		String navegacion = null;

		List<Usuario> usuarios = JPADAOFactory.getFactory().getUsuarioDAO()
				.find(new String[] { "username", "password" }, new String[] { this.getNombre(), this.getContrasenia() }, null, 0, 0);

		if (usuarios.size() == 1) {

			Usuario usuario = usuarios.get(0);

			//Solo puede darse que ingrese un Administrador o un empleado, pero nunca un cliente.
			//TODO: Poner el codigo de Cliente en Enumeracion.
			switch (usuario.getRol().getId()) {
			case 3:
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UsuarioBean", usuario);
				navegacion = "admin";
				break;
			case 1:
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UsuarioBean", usuario);
				navegacion = "empleado";
				break;
			default:
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario o clave incorrecta!!", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				break;
			}
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario o clave incorrecta!!", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return navegacion;

	}

}
