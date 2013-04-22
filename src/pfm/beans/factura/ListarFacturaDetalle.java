package pfm.beans.factura;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.RowEditEvent;

import pfm.dao.FacturaDAO;
import pfm.dao.FacturaDetalleDAO;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;

@ManagedBean(name = "listarFacturaDetalle")
public class ListarFacturaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.facturaDAO}")
	private FacturaDAO facturaDAO;
	@ManagedProperty(value = "#{DAOFactory.facturaDetalleDAO}")
	private FacturaDetalleDAO facturaDetalleDAO;
	@ManagedProperty(value = "#{modificarFacturaDetalle}")
	private ModificarFacturaDetalle modificarFacturaDetalleBEAN;
	private Factura factura = new Factura();
	private int id;
	private List<FacturaDetalle> lista;
	private FacturaDetalle[] selectedFacturaDetalle;

	public ListarFacturaDetalle() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		if (request.getParameter("id") != null) {
			setId(Integer.parseInt(request.getParameter("id")));
			// setId(1);
		} else {
			// setId(1);
			setId(0);
		}
	}

	public FacturaDAO getFacturaDAO() {
		return facturaDAO;
	}

	public void setFacturaDAO(FacturaDAO facturaDAO) {
		this.facturaDAO = facturaDAO;
	}

	public FacturaDetalleDAO getFacturaDetalleDAO() {
		return facturaDetalleDAO;
	}

	public void setFacturaDetalleDAO(FacturaDetalleDAO facturaDetalleDAO) {
		this.facturaDetalleDAO = facturaDetalleDAO;
	}

	public void setModificarFacturaDetalleBEAN(
			ModificarFacturaDetalle modificarFacturaDetalleBEAN) {
		this.modificarFacturaDetalleBEAN = modificarFacturaDetalleBEAN;
	}

	public ModificarFacturaDetalle getModificarFacturaDetalleBEAN() {
		return modificarFacturaDetalleBEAN;
	}

	public Factura getFactura() {
		setFactura(facturaDAO.read(getId()));
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public List<FacturaDetalle> getLista() {
		try {
			setLista(facturaDetalleDAO.getFacturaDetalleByFactura(getFactura()));
		} catch (Exception e) {
			System.out.println("ERROR <<ModificarFactura>>: getLista()" + e);
		}
		return lista;
	}

	public void setLista(List<FacturaDetalle> lista) {
		this.lista = lista;
	}

	public FacturaDetalle[] getSelectedFacturaDetalle() {
		return selectedFacturaDetalle;
	}

	public void setSelectedFacturaDetalle(
			FacturaDetalle[] selectedFacturaDetalle) {
		this.selectedFacturaDetalle = selectedFacturaDetalle;
	}

	public void onModificar(RowEditEvent event) {

		modificarFacturaDetalleBEAN.setFacturaDetalle((FacturaDetalle) event
				.getObject());
		modificarFacturaDetalleBEAN.modificar();

	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Producto cancelado",
				String.valueOf(((FacturaDetalle) event.getObject())
						.getBodegaDetalle().getProducto()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
