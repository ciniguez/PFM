package pfm.dao;

import pfm.entidades.Descuento;

public interface DescuentoDAO extends GenericDAO<Descuento, Integer> {

	public double getValorDescuentoByFecha(int id, boolean eliminado);
}
