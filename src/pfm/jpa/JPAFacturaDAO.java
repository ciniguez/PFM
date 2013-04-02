package pfm.jpa;

import pfm.dao.FacturaDAO;
import pfm.entidades.Factura;

public class JPAFacturaDAO extends JPAGenericDAO<Factura, Integer> implements
		FacturaDAO {

	public JPAFacturaDAO() {
		super(Factura.class);
	}

}
