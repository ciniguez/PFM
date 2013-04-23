package pfm.dao;

import java.util.List;

import pfm.entidades.Agencia;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Usuario;

public interface FacturaDAO extends GenericDAO<Factura, Integer> {

	public List<Factura> getFacturasByAgencia(Agencia agencia, boolean pendiente);

	/**
	 * Lista las facturas pendientes y no eliminadas (Carro de compras) del
	 * Cliente.
	 * 
	 * @param cliente
	 * @return
	 */
	public List<Factura> getFacturasPendientesByCliente(Usuario cliente);

	public void setTotalesFactura(Factura factura,
			List<FacturaDetalle> listaFacturaDetalle);
}
