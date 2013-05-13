package pfm.beans.factura;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.beans.reportes.ReporteMenu;
import pfm.dao.FacturaDAO;
import pfm.dao.FacturaDetalleDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Usuario;

@ManagedBean(name = "listarFacturaGenerada")
@RequestScoped
public class ListarFacturaGenerada implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Factura> listaFacturas;
	private List<FacturaDetalle> listaFacturaDetalle;
	private Usuario empleado;
	private EmpleadoAgencia empleadoAgencia;
	private List<Factura> filteredFacturas;
	@ManagedProperty(value = "#{DAOFactory.facturaDAO}")
	private FacturaDAO facturaDAO;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO empleadoDAO;
	@ManagedProperty(value = "#{DAOFactory.facturaDetalleDAO}")
	private FacturaDetalleDAO facturaDetalleDAO;
	@ManagedProperty(value = "#{reporte}")
	private ReporteMenu reporteMenuBEAN;
	private Factura selectedFactura;

	public ListarFacturaGenerada() {
		this.empleado = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("UsuarioBean");
	}

	public List<FacturaDetalle> getListaFacturaDetalle() {
		try {
			setListaFacturaDetalle(facturaDetalleDAO
					.getFacturaDetalleByFactura(getSelectedFactura(), false));
		} catch (Exception e) {
			System.out.println("ERROR <<ListarFacturaPagada>>: getLista()" + e);
		}
		return listaFacturaDetalle;
	}

	public void setListaFacturaDetalle(List<FacturaDetalle> listaFacturaDetalle) {
		this.listaFacturaDetalle = listaFacturaDetalle;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	public FacturaDAO getFacturaDAO() {
		return facturaDAO;
	}

	public void setFacturaDAO(FacturaDAO facturaDAO) {
		this.facturaDAO = facturaDAO;
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

	public EmpleadoAgencia getEmpleadoAgencia() {
		return empleadoAgencia;
	}

	public void setEmpleadoAgencia(EmpleadoAgencia empleadoAgencia) {
		this.empleadoAgencia = empleadoAgencia;
	}

	public String onImprimir() {
		reporteMenuBEAN.imprimirFactura(getSelectedFactura());
		return null;

	}

	public List<Factura> getListaFacturas() {
		try {
			// TODO: poner en enumeracion los roles
			if (getEmpleado().getRol().getId() == 1) {
				setListaFacturas(facturaDAO
						.getFacturasGeneradasByEmpleado(this.getEmpleado()));
				// TODO: poner en enumeracion los roles
			} else if (getEmpleado().getRol().getId() == 3) {
				setListaFacturas(facturaDAO.getFacturasGeneradas());
			}

		} catch (Exception e) {
			System.out
					.println("ERROR <<ListarFacturaPagadas>>: getListaFacturas()"
							+ e);
		}
		return this.listaFacturas;
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

	public ReporteMenu getReporteMenuBEAN() {
		return reporteMenuBEAN;
	}

	public void setReporteMenuBEAN(ReporteMenu reporteMenuBEAN) {
		this.reporteMenuBEAN = reporteMenuBEAN;
	}

	public Factura getSelectedFactura() {
		return selectedFactura;
	}

	public void setSelectedFactura(Factura selectedFactura) {
		this.selectedFactura = selectedFactura;
	}

}
