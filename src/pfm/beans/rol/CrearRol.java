package pfm.beans.rol;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.RolDAO;
import pfm.entidades.Rol;

@ManagedBean(name = "crearRol")
public class CrearRol implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.rolDAO}")
	private RolDAO rolDAO;
	private Rol rol = new Rol();

	public CrearRol() {

	}

	public RolDAO getRolDAO() {
		return rolDAO;
	}

	public void setRolDAO(RolDAO rolDAO) {
		this.rolDAO = rolDAO;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String crear() {
		try {
			rolDAO.create(rol);
			FacesMessage msg = new FacesMessage("Rol creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Rol no creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarRol";
	}

}
