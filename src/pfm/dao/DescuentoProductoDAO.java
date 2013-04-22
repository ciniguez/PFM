package pfm.dao;

import pfm.entidades.DescuentoProducto;
import pfm.entidades.Producto;

public interface DescuentoProductoDAO extends
		GenericDAO<DescuentoProducto, Integer> {

	public int getDescuentoId(Producto producto, boolean eliminado);
}
