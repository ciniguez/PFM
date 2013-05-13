package pfm.beans.rol;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.RolDAO;
import pfm.entidades.Rol;

@ManagedBean(name = "altaRol")
@RequestScoped
public class AltaRol implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.rolDAO}")
	private RolDAO rolDAO;
	private Rol rol;

	public AltaRol() {

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

	public String alta() {
		try {
			rol.setEliminado(false);
			getRolDAO().update(rol);
			FacesMessage msg = new FacesMessage("Rol dado de alta",
					String.valueOf(rol.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Rol no dado de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}

		return "listarRol";
	}
}
