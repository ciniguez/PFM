package pfm.jpa;


import pfm.dao.FacturaDetalleDAO;
import pfm.entidades.FacturaDetalle;

public class JPAFacturaDetalleDAO extends JPAGenericDAO<FacturaDetalle, Integer> implements FacturaDetalleDAO {

	public JPAFacturaDetalleDAO() {
		super(FacturaDetalle.class);
	}

	
}
