package pfm.beans.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import pfm.dao.RolDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.Rol;
import pfm.entidades.Usuario;

@ManagedBean(name = "listarUsuario")
@RequestScoped
public class ListarUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO usuarioDAO;
	@ManagedProperty(value = "#{DAOFactory.rolDAO}")
	private RolDAO rolDAO;
	@ManagedProperty(value = "#{modificarUsuario}")
	private ModificarUsuario modificarUsuarioBEAN;
	@ManagedProperty(value = "#{bajaUsuario}")
	private BajaUsuario bajaUsuarioBEAN;
	@ManagedProperty(value = "#{altaUsuario}")
	private AltaUsuario altaUsuarioBEAN;
	private List<Usuario> lista;
	private List<Usuario> filtered;
	private SelectItem[] roles;
	private String rol;
	private Usuario[] selectedUsuarios;

	public ListarUsuario() {

	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public ModificarUsuario getModificarUsuarioBEAN() {
		return modificarUsuarioBEAN;
	}

	public void setModificarUsuarioBEAN(ModificarUsuario modificarUsuarioBEAN) {
		this.modificarUsuarioBEAN = modificarUsuarioBEAN;
	}

	public BajaUsuario getBajaUsuarioBEAN() {
		return bajaUsuarioBEAN;
	}

	public void setBajaUsuarioBEAN(BajaUsuario bajaUsuarioBEAN) {
		this.bajaUsuarioBEAN = bajaUsuarioBEAN;
	}

	public AltaUsuario getAltaUsuarioBEAN() {
		return altaUsuarioBEAN;
	}

	public void setAltaUsuarioBEAN(AltaUsuario altaUsuarioBEAN) {
		this.altaUsuarioBEAN = altaUsuarioBEAN;
	}

	public List<Usuario> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(usuarioDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public List<Usuario> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Usuario> filtered) {
		this.filtered = filtered;
	}

	public Usuario[] getSelectedUsuarios() {
		return selectedUsuarios;
	}

	public void setSelectedUsuarios(Usuario[] selectedUsuarios) {
		this.selectedUsuarios = selectedUsuarios;
	}

	public String onCrear() {
		return "crearUsuario";
	}

	public void onModificar(RowEditEvent event) {
		modificarUsuarioBEAN.setUsuario((Usuario) event.getObject());
		modificarUsuarioBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Usuario cancelada", String.valueOf(((Usuario) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedUsuarios.length > 0) {
			for (Usuario e : selectedUsuarios) {
				bajaUsuarioBEAN.setUsuario(e);
				bajaUsuarioBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error", "Debe seleccionar uno o mas usuarios");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarUsuario";
	}

	public String onAlta() {
		if (selectedUsuarios.length > 0) {
			for (Usuario e : selectedUsuarios) {
				altaUsuarioBEAN.setUsuario(e);
				altaUsuarioBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error", "Debe seleccionar uno o mas usuarios");
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