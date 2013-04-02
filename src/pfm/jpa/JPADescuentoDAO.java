package pfm.jpa;


import pfm.dao.DescuentoDAO;
import pfm.entidades.Descuento;

public class JPADescuentoDAO extends JPAGenericDAO<Descuento, Integer> implements DescuentoDAO {

	public JPADescuentoDAO() {
		super(Descuento.class);
	}
}
