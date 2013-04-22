package pfm.beans.factura;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.EmpleadoAgenciaDAO;
import pfm.dao.FacturaDAO;
import pfm.dao.FacturaDetalleDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;

@ManagedBean(name = "listarFacturaPendiente")
@SessionScoped
public class ListarFacturaPendiente implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.facturaDAO}")
	private FacturaDAO facturaDAO;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO empleadoDAO;
	@ManagedProperty(value = "#{DAOFactory.empleadoAgenciaDAO}")
	private EmpleadoAgenciaDAO empleadoAgenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.facturaDetalleDAO}")
	private FacturaDetalleDAO facturaDetalleDAO;
	@ManagedProperty(value = "#{modificarFacturaDetalle}")
	private ModificarFacturaDetalle modificarFacturaDetalleBEAN;
	private List<Factura> listaFacturas;
	private List<Factura> filteredFacturas;
	private Factura[] selectedFacturas;
	private Factura selectedFactura;
	private List<FacturaDetalle> listaFacturaDetalle;
	private FacturaDetalle[] selectedFacturaDetalle;

	public ListarFacturaPendiente() {

	}

	public FacturaDAO getFacturaDAO() {
		return facturaDAO;
	}

	public void setFacturaDAO(FacturaDAO facturaDAO) {
		this.facturaDAO = facturaDAO;
	}

	public EmpleadoAgenciaDAO getEmpleadoAgenciaDAO() {
		return empleadoAgenciaDAO;
	}

	public void setEmpleadoAgenciaDAO(EmpleadoAgenciaDAO empleadoAgenciaDAO) {
		this.empleadoAgenciaDAO = empleadoAgenciaDAO;
	}

	public UsuarioDAO getEmpleadoDAO() {
		return empleadoDAO;
	}

	public void setEmpleadoDAO(UsuarioDAO empleadoDAO) {
		this.empleadoDAO = empleadoDAO;
	}

	public FacturaDetalleDAO getFacturaDetalleDAO() {
		return facturaDetalleDAO;
	}

	public void setFacturaDetalleDAO(FacturaDetalleDAO facturaDetalleDAO) {
		this.facturaDetalleDAO = facturaDetalleDAO;
	}

	public ModificarFacturaDetalle getModificarFacturaDetalleBEAN() {
		return modificarFacturaDetalleBEAN;
	}

	public void setModificarFacturaDetalleBEAN(
			ModificarFacturaDetalle modificarFacturaDetalleBEAN) {
		this.modificarFacturaDetalleBEAN = modificarFacturaDetalleBEAN;
	}

	public List<Factura> getListaFacturas() {
		try {
			// obtiene la agencia del empleado
			EmpleadoAgencia empleadoAgencia = new EmpleadoAgencia();
			// AQUI DEBE IR EL ID DEL EMPLEADO Q HAYA INICIADO SESION
			empleadoAgencia = empleadoAgenciaDAO
					.getAgenciaByEmpleado(empleadoDAO.read(2));
			// setea la lista de facturas con la agencia obtenida del empleado
			// anteriormente
			setListaFacturas(facturaDAO.getFacturasByAgencia(
					empleadoAgencia.getAgencia(), false, true));
		} catch (Exception e) {
			System.out
					.println("ERROR <<ListarFacturaPendiente>>: getListaFacturas()"
							+ e);
		}
		return listaFacturas;
	}

	public void setListaFacturas(List<Factura> listaFacturas) {
		this.listaFacturas = listaFacturas;
	}

	public List<Factura> getFilteredFacturas() {
		return filteredFacturas;
	}

	public void setFilteredFacturas(List<Factura> filteredFacturas) {
		this.filteredFacturas = filteredFacturas;
	}

	public Factura[] getSelectedFacturas() {
		return selectedFacturas;
	}

	public void setSelectedFacturas(Factura[] selectedFacturas) {
		this.selectedFacturas = selectedFacturas;
	}

	public Factura getSelectedFactura() {
		return selectedFactura;
	}

	public void setSelectedFactura(Factura selectedFactura) {
		this.selectedFactura = selectedFactura;
	}

	public List<FacturaDetalle> getListaFacturaDetalle() {
		try {
			setListaFacturaDetalle(facturaDetalleDAO
					.getFacturaDetalleByFactura(getSelectedFactura()));
		} catch (Exception e) {
			System.out.println("ERROR <<ModificarFactura>>: getLista()" + e);
		}
		return listaFacturaDetalle;
	}

	public void setListaFacturaDetalle(List<FacturaDetalle> listaFacturaDetalle) {
		this.listaFacturaDetalle = listaFacturaDetalle;
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
