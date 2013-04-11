package pfm.beans.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pfm.dao.RolDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.Rol;
import pfm.entidades.Usuario;

@ManagedBean(name = "crearUsuario")
public class CrearUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO usuarioDAO;
	@ManagedProperty(value = "#{DAOFactory.rolDAO}")
	private RolDAO rolDAO;
	private Usuario usuario = new Usuario();
	private SelectItem[] roles;
	private String rol;

	public CrearUsuario() {

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

	public String crear() {
		try {
			usuarioDAO.create(usuario);
			FacesMessage msg = new FacesMessage("Usuario creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Usuario no creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarUsuario";
	}

	public SelectItem[] getRoles() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Rol> listaRoles = new ArrayList<Rol>();
		listaRoles = rolDAO.find(attributes, values, order, index, size);
		this.roles = new SelectItem[listaRoles.size()];
		for (Rol e : listaRoles) {
			this.roles[i] = new SelectItem(e.getId(), e.getNombre());
			i++;
		}
		return roles;
	}

	public void setRoles(SelectItem[] roles) {
		this.roles = roles;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public RolDAO getRolDAO() {
		return rolDAO;
	}

	public void setRolDAO(RolDAO rolDAO) {
		this.rolDAO = rolDAO;
	}

}