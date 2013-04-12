package pfm.beans.empleadoAgencia;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.EmpleadoAgenciaDAO;
import pfm.entidades.EmpleadoAgencia;

@ManagedBean(name = "modificarEmpleadoAgencia")
public class ModificarEmpleadoAgencia implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empleadoAgenciaDAO}")
	private EmpleadoAgenciaDAO empleadoAgenciaDAO;
	private EmpleadoAgencia empleadoAgencia;

	public ModificarEmpleadoAgencia() {

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

	public String modificar() {
		try {
			getEmpleadoAgenciaDAO().update(empleadoAgencia);
			FacesMessage msg = new FacesMessage(
					"Empleado por agencia actualizado",
					String.valueOf(empleadoAgencia.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Empleado por agencia no actualizado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpleadoAgencia";
	}
}
