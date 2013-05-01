package pfm.dao;

import pfm.entidades.Descuento;

public interface DescuentoDAO extends GenericDAO<Descuento, Integer> {

	public Descuento getValorDescuentoByFecha(int id, boolean eliminado);
}
