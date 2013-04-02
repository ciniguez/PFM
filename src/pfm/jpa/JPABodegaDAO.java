package pfm.jpa;

import pfm.dao.BodegaDAO;
import pfm.entidades.Bodega;

public class JPABodegaDAO extends JPAGenericDAO<Bodega, Integer> implements BodegaDAO {

	public JPABodegaDAO() {
		super(Bodega.class);
	}

}
