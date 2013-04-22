package pfm.beans.usuario;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pfm.dao.UsuarioDAO;
import pfm.entidades.Usuario;

@ManagedBean(name = "bajaUsuario")
@SessionScoped
public class BajaUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO usuarioDAO;
	private Usuario usuario;

	public BajaUsuario() {
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

	public String baja() {
		try {
			usuario.setEliminado(true);
			getUsuarioDAO().update(usuario);
			FacesMessage msg = new FacesMessage("Usuario dada de baja", String.valueOf(usuario.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Usuario no dada de baja");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarUsuario";
	}
}