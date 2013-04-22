package pfm.dao;

import java.util.List;

import pfm.entidades.Agencia;
import pfm.entidades.Factura;

public interface FacturaDAO extends GenericDAO<Factura, Integer> {

	public List<Factura> getFacturasByAgencia(Agencia agencia, boolean pagado,
			boolean pendiente);
}
