package pfm.dao;

import java.util.List;

import pfm.entidades.Agencia;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Usuario;

public interface FacturaDAO extends GenericDAO<Factura, Integer> {

	public List<Factura> getFacturasPendientesByAgencia(Agencia agencia,
			boolean pendiente);

	public List<Factura> getFacturasPendientes(boolean pendiente);

	/**
	 * Lista las facturas pendientes y no eliminadas (Carro de compras) del
	 * Cliente.
	 * 
	 * @autor Carlos Iniguez
	 * @param cliente
	 * @return
	 */
	public List<Factura> getFacturasPendientesByCliente(Usuario cliente);

	/**
	 * Lista las Facturas Pendientes dado el Usuario Beneficiario y la Agencia a
	 * la cual pertenece USO: Servicios REST -> Listar Facturas Pendientes
	 * (Historial de Carros de Compra) del usuario logeado en el movil y con la
	 * agencia indicada.
	 * 
	 * @param cliente
	 * @param agencia
	 * @return
	 */
	public List<Factura> getFacturasPendientesByClienteAndAgencia(
			Usuario cliente, Agencia agencia);

	public void setTotalesFactura(Factura factura,
			List<FacturaDetalle> listaFacturaDetalle);

	/**
	 * Lista la facturacion realizada por un Empleado USO: Reportes
	 * 
	 * @author Carlos Iniguez
	 * @param empleado
	 *            Empleado del tipo Usuario que ha realizado la facturacion.
	 * @return List<Factura> Lista de Facturas
	 */
	public List<Factura> getFacturasGeneradasByEmpleado(Usuario empleado);

	/**
	 * Lista la facturacion total USO: Reportes para el Administrador
	 * 
	 * @author Carlos Iniguez
	 * @return Lista de Facturas
	 */
	public List<Factura> getFacturasGeneradas();
}
