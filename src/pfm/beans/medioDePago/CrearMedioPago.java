package pfm.beans.medioDePago;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.MedioPagoDAO;
import pfm.entidades.MedioDePago;

@ManagedBean(name = "crearMedioPago")
public class CrearMedioPago implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.medioPagoDAO}")
	private MedioPagoDAO medioPagoDAO;
	private MedioDePago medioPago = new MedioDePago();

	public CrearMedioPago() {

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

	public String crear() {
		try {
			medioPagoDAO.create(medioPago);
			FacesMessage msg = new FacesMessage("Medio de Pago creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Medio de Pago no creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarMedioPago";
	}
}
