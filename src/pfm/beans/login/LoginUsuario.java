package pfm.beans.login;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pfm.dao.UsuarioDAO;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "loginUsuario")
@SessionScoped
public class LoginUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO usuarioDAO;
	private Usuario usuario = new Usuario();
	private boolean isLogeado = false;
	
	public String aviso(){
		return "estoy activo";
	}
	
	//GETTERS AND SETTERS
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public String logout() {
		this.setLogeado(false);
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		return "logout";
	}

	//OPERACIONES
	public String procesar() {
		String navegacion = null;

		List<Usuario> usuarios = JPADAOFactory.getFactory().getUsuarioDAO()
				.find(new String[] { "username", "password" }, new String[] { this.getUsuario().getUsername(), this.getUsuario().getPassword() }, null, 0, 0);

		if (usuarios.size() == 1) {

			this.setUsuario(usuarios.get(0));

			//Solo puede darse que ingrese un Administrador o un empleado, pero nunca un cliente.
			//TODO: Poner el codigo de Cliente en Enumeracion.
			switch (this.getUsuario().getRol().getId()) {
			case 3:
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UsuarioBean", this.getUsuario());
				this.setLogeado(true);
				navegacion = "admin";
				break;
			case 1:
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UsuarioBean", this.getUsuario());
				this.setLogeado(true);
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogeado() {
		return isLogeado;
	}

	public void setLogeado(boolean isLogeado) {
		this.isLogeado = isLogeado;
	}

}
