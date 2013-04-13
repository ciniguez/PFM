package pfm.beans.factura;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pfm.dao.EmpleadoAgenciaDAO;
import pfm.dao.FacturaDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Factura;

@ManagedBean(name = "listarFactura")
public class ListarFactura implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.facturaDAO}")
	private FacturaDAO facturaDAO;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO empleadoDAO;
	@ManagedProperty(value = "#{DAOFactory.empleadoAgenciaDAO}")
	private EmpleadoAgenciaDAO empleadoAgenciaDAO;
	private List<Factura> lista;
	private List<Factura> filtered;
	private Factura[] selectedFacturas;

	public ListarFactura() {

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

	public List<Factura> getLista() {
		EmpleadoAgencia empleadoAgencia = new EmpleadoAgencia();
		// AQUI DEBE IR EL ID DEL EMPLEADO Q HAYA INICIADO SESION
		empleadoAgencia = empleadoAgenciaDAO.getAgenciaByEmpleado(empleadoDAO
				.read(1));
		setLista(facturaDAO.getFacturasByEmpleado(empleadoAgencia));
		return lista;
	}

	public void setLista(List<Factura> lista) {
		this.lista = lista;
	}

	public List<Factura> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Factura> filtered) {
		this.filtered = filtered;
	}

	public Factura[] getSelectedFacturas() {
		return selectedFacturas;
	}

	public void setSelectedFacturas(Factura[] selectedFacturas) {
		this.selectedFacturas = selectedFacturas;
	}

}
