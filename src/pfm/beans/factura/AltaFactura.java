package pfm.beans.factura;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.FacturaDAO;
import pfm.entidades.Factura;

@ManagedBean(name = "altaFactura")
@RequestScoped
public class AltaFactura implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.facturaDAO}")
	private FacturaDAO facturaDAO;
	private Factura factura;

	public AltaFactura() {

	}

	public FacturaDAO getFacturaDAO() {
		return facturaDAO;
	}

	public void setFacturaDAO(FacturaDAO facturaDAO) {
		this.facturaDAO = facturaDAO;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public String alta() {
		try {
			factura.setEliminado(false);
			getFacturaDAO().update(factura);
			FacesMessage msg = new FacesMessage("Factura dada de alta",
					String.valueOf(factura.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Factura no dada de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}

		return "listarFacturaPendiente";
	}
}
