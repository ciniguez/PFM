package pfm.jpa;

import pfm.dao.MarcaDAO;
import pfm.entidades.Marca;

public class JPAMarcaDAO extends JPAGenericDAO<Marca, Integer> implements
		MarcaDAO {

	public JPAMarcaDAO() {
		super(Marca.class);
	}

}
