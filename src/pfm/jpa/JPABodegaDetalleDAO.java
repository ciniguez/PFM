package pfm.jpa;

import java.util.List;

import javax.persistence.Query;

import pfm.dao.BodegaDetalleDAO;
import pfm.entidades.Bodega;
import pfm.entidades.BodegaDetalle;
import pfm.entidades.Producto;

public class JPABodegaDetalleDAO extends JPAGenericDAO<BodegaDetalle, Integer>
		implements BodegaDetalleDAO {

	public JPABodegaDetalleDAO() {
		super(BodegaDetalle.class);
	}

	@Override
	public List<BodegaDetalle> getBodegaDetalleByBodegaAndProducto(
			Producto producto, Bodega bodega) {
		Query query = em
				.createNamedQuery("getBodegaDetalleByBodegaAndProducto");
		query.setParameter("producto", producto);
		query.setParameter("bodega", bodega);
		@SuppressWarnings("unchecked")
		List<BodegaDetalle> resultado = query.getResultList();
		return resultado;
	}
}
