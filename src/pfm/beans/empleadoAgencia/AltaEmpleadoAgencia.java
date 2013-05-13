package pfm.beans.empleadoAgencia;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.EmpleadoAgenciaDAO;
import pfm.entidades.EmpleadoAgencia;

@ManagedBean(name = "altaEmpleadoAgencia")
@RequestScoped
public class AltaEmpleadoAgencia implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empleadoAgenciaDAO}")
	private EmpleadoAgenciaDAO empleadoAgenciaDAO;
	private EmpleadoAgencia empleadoAgencia;

	public AltaEmpleadoAgencia() {
	}

	public EmpleadoAgenciaDAO getEmpleadoAgenciaDAO() {
		return empleadoAgenciaDAO;
	}

	public void setEmpleadoAgenciaDAO(EmpleadoAgenciaDAO empleadoAgenciaDAO) {
		this.empleadoAgenciaDAO = empleadoAgenciaDAO;
	}

	public EmpleadoAgencia getEmpleadoAgencia() {
		return empleadoAgencia;
	}

	public void setEmpleadoAgencia(EmpleadoAgencia empleadoAgencia) {
		this.empleadoAgencia = empleadoAgencia;
	}

	public String alta() {
		try {
			empleadoAgencia.setEliminado(false);
			getEmpleadoAgenciaDAO().update(empleadoAgencia);
			FacesMessage msg = new FacesMessage(
					"Empleado por agencia dado de alta",
					String.valueOf(empleadoAgencia.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Empleado por agencia no dado de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}
		return "listarEmpleadoAgencia";
	}
}
