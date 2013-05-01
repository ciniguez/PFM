package pfm.jpa;

import javax.persistence.Query;

import pfm.dao.DescuentoProductoDAO;
import pfm.entidades.DescuentoProducto;
import pfm.entidades.Producto;

public class JPADescuentoProductoDAO extends
		JPAGenericDAO<DescuentoProducto, Integer> implements
		DescuentoProductoDAO {

	public JPADescuentoProductoDAO() {
		super(DescuentoProducto.class);
	}

	@Override
	public int getDescuentoId(Producto producto, boolean eliminado) {
		try {
			Query query = em.createNamedQuery("getDescuentoId");
			query.setParameter("producto", producto);
			query.setParameter("eliminado", eliminado);
			DescuentoProducto resultado = (DescuentoProducto) query
					.getSingleResult();
			return resultado.getDescuento().getId();

		} catch (Exception e) {
			//System.out.println("ERROR: getDescuentoId " + e);
			return 0;
		}
	}
}
