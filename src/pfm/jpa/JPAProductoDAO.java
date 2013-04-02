package pfm.jpa;

import pfm.dao.ProductoDAO;
import pfm.entidades.Producto;

public class JPAProductoDAO extends JPAGenericDAO<Producto, Integer> implements
		ProductoDAO {

	public JPAProductoDAO() {
		super(Producto.class);
	}

}
