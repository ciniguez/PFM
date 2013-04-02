package pfm.jpa;

import pfm.dao.MedioPagoDAO;
import pfm.entidades.MedioDePago;

public class JPAMedioPagoDAO extends JPAGenericDAO<MedioDePago, Integer>
		implements MedioPagoDAO {

	public JPAMedioPagoDAO() {
		super(MedioDePago.class);
	}
}
