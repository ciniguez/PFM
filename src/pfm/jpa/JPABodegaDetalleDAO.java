package pfm.jpa;

import pfm.dao.BodegaDetalleDAO;
import pfm.entidades.BodegaDetalle;

public class JPABodegaDetalleDAO extends JPAGenericDAO<BodegaDetalle, Integer>
		implements BodegaDetalleDAO {

	public JPABodegaDetalleDAO() {
		super(BodegaDetalle.class);
	}
}
