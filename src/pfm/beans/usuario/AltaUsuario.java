package pfm.beans.usuario;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.UsuarioDAO;
import pfm.entidades.Usuario;

@ManagedBean(name = "altaUsuario")
@RequestScoped
public class AltaUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO usuarioDAO;
	private Usuario usuario;

	public AltaUsuario() {
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String alta() {
		try {
			usuario.setEliminado(false);
			getUsuarioDAO().update(usuario);
			FacesMessage msg = new FacesMessage("Usuario dada de alta", String.valueOf(usuario.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Usuario no dada de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}
		return "listarUsuario";
	}
}