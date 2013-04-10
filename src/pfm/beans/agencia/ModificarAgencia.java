package pfm.beans.agencia;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.AgenciaDAO;
import pfm.entidades.Agencia;

@ManagedBean(name = "modificarAgencia")
public class ModificarAgencia implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	private Agencia agencia;

	public ModificarAgencia() {

	}

	public AgenciaDAO getAgenciaDAO() {
		return agenciaDAO;
	}

	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public String modificar() {
		try {
			getAgenciaDAO().update(agencia);
			FacesMessage msg = new FacesMessage("Agencia actualizada",
					String.valueOf(agencia.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Agencia no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarAgencia";
	}
}
