package pfm.beans.factura;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.FacturaDAO;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Factura;

@ManagedBean(name = "generarFactura")
@RequestScoped
public class GenerarFactura implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.facturaDAO}")
	private FacturaDAO facturaDAO;
	private Factura factura;
	private EmpleadoAgencia empleadoAgencia;

	public GenerarFactura() {

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

	public EmpleadoAgencia getEmpleadoAgencia() {
		return empleadoAgencia;
	}

	public void setEmpleadoAgencia(EmpleadoAgencia empleadoAgencia) {
		this.empleadoAgencia = empleadoAgencia;
	}

	public String generar() {
		try {
			if (factura.getEliminado() == false) {
				if (factura.isPagado()) {
					factura.setEmpleadoAgencia(empleadoAgencia);
					//factura.setPendiente(false);
					getFacturaDAO().update(factura);
					FacesMessage msg = new FacesMessage("Factura generada",
							String.valueOf(factura.getId()));
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					FacesMessage msg = new FacesMessage("Error",
							"La factura no ha sido pagada");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				FacesMessage msg = new FacesMessage("Error",
						"La factura se encuentra dada de baja");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Factura no generada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}

		return "listarFacturaPendiente";
	}
}
