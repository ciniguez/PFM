package pfm.beans.medioPago;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.MedioPagoDAO;
import pfm.entidades.MedioDePago;

@ManagedBean(name = "modificarMedioPago")
public class ModificarMedioPago implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.medioPagoDAO}")
	private MedioPagoDAO medioPagoDAO;
	private MedioDePago medioPago;

	public ModificarMedioPago() {
	}

	public MedioPagoDAO getMedioPagoDAO() {
		return medioPagoDAO;
	}

	public void setMedioPagoDAO(MedioPagoDAO medioPagoDAO) {
		this.medioPagoDAO = medioPagoDAO;
	}

	public MedioDePago getMedioPago() {
		return medioPago;
	}

	public void setMedioPago(MedioDePago medioPago) {
		this.medioPago = medioPago;
	}

	public String modificar() {
		try {
			getMedioPagoDAO().update(medioPago);
			FacesMessage msg = new FacesMessage("MedioPago actualizada", String.valueOf(medioPago.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "MedioPago no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarMedioPago";
	}
}