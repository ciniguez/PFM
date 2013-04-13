package pfm.dao;

import java.util.List;

import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Factura;

public interface FacturaDAO extends GenericDAO<Factura, Integer> {

	public List<Factura> getFacturasByEmpleado(EmpleadoAgencia empleadoAgencia);
}
