package pfm.beans.factura;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
	@ManagedProperty(value = "#{generarFactura}")
	private GenerarFactura generarFacturaBEAN;
	@ManagedProperty(value = "#{bajaFactura}")
	private BajaFactura bajaFacturaBEAN;
	@ManagedProperty(value = "#{altaFactura}")
	private AltaFactura altaFacturaBEAN;
	private EmpleadoAgencia empleadoAgencia;
	private List<Factura> listaFacturas;
	private List<Factura> filteredFacturas;
	private Factura[] selectedFacturas;
	private Factura selectedFactura;
	private List<FacturaDetalle> listaFacturaDetalle;

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

	public GenerarFactura getGenerarFacturaBEAN() {
		return generarFacturaBEAN;
	}

	public void setGenerarFacturaBEAN(GenerarFactura generarFacturaBEAN) {
		this.generarFacturaBEAN = generarFacturaBEAN;
	}

	public BajaFactura getBajaFacturaBEAN() {
		return bajaFacturaBEAN;
	}

	public void setBajaFacturaBEAN(BajaFactura bajaFacturaBEAN) {
		this.bajaFacturaBEAN = bajaFacturaBEAN;
	}

	public AltaFactura getAltaFacturaBEAN() {
		return altaFacturaBEAN;
	}

	public void setAltaFacturaBEAN(AltaFactura altaFacturaBEAN) {
		this.altaFacturaBEAN = altaFacturaBEAN;
	}

	public EmpleadoAgencia getEmpleadoAgencia() {
		// AQUI DEBE IR EL ID DEL EMPLEADO Q HAYA INICIADO SESION
		setEmpleadoAgencia(empleadoAgenciaDAO.getAgenciaByEmpleado(empleadoDAO
				.read(2)));

		return empleadoAgencia;
	}

	public void setEmpleadoAgencia(EmpleadoAgencia empleadoAgencia) {
		this.empleadoAgencia = empleadoAgencia;
	}

	public List<Factura> getListaFacturas() {
		try {

			// setea la lista de facturas con la agencia obtenida del empleado
			// anteriormente
			setListaFacturas(facturaDAO.getFacturasByAgencia(
					getEmpleadoAgencia().getAgencia(), true));
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
					.getFacturaDetalleByFactura(getSelectedFactura(), false));
		} catch (Exception e) {
			System.out.println("ERROR <<ModificarFactura>>: getLista()" + e);
		}
		return listaFacturaDetalle;
	}

	public void setListaFacturaDetalle(List<FacturaDetalle> listaFacturaDetalle) {
		this.listaFacturaDetalle = listaFacturaDetalle;
	}

	public String onGenerar() {
		if (selectedFacturas.length > 0) {
			for (Factura f : selectedFacturas) {
				generarFacturaBEAN.setEmpleadoAgencia(getEmpleadoAgencia());
				generarFacturaBEAN.setFactura(f);
				generarFacturaBEAN.generar();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas facturas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarFacturaPendiente";
	}

	public String onImprimir() {
		if (selectedFacturas.length > 0) {
			// generar codigo para imprimir las facturas seleccionadas
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas facturas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarFacturaPendiente";
	}

	public String onBaja() {

		if (selectedFacturas.length > 0) {
			for (Factura f : selectedFacturas) {
				bajaFacturaBEAN.setFactura(f);
				bajaFacturaBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas facturas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarFacturaPendiente";
	}

	public String onAlta() {
		if (selectedFacturas.length > 0) {
			for (Factura f : selectedFacturas) {
				altaFacturaBEAN.setFactura(f);
				altaFacturaBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas facturas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarFacturaPendiente";
	}
}
