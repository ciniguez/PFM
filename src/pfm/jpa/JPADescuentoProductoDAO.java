package pfm.jpa;

import pfm.dao.DescuentoProductoDAO;
import pfm.entidades.DescuentoProducto;

public class JPADescuentoProductoDAO extends
		JPAGenericDAO<DescuentoProducto, Integer> implements
		DescuentoProductoDAO {

	public JPADescuentoProductoDAO() {
		super(DescuentoProducto.class);
	}
}
