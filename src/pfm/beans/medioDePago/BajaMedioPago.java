package pfm.beans.medioDePago;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.MedioPagoDAO;
import pfm.entidades.MedioDePago;

@ManagedBean(name = "bajaMedioPago")
@RequestScoped
public class BajaMedioPago implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.medioPagoDAO}")
	private MedioPagoDAO medioPagoDAO;
	private MedioDePago medioPago;

	public BajaMedioPago() {

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

	public String baja() {
		try {
			medioPago.setEliminado(true);
			getMedioPagoDAO().update(medioPago);
			FacesMessage msg = new FacesMessage("Medio de Pago dado de baja",
					String.valueOf(medioPago.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Medio de pago no dado de baja");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}
		return "listarMedioPago";
	}

}
