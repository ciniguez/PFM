package pfm.jpa;

import pfm.dao.AgenciaDAO;
import pfm.entidades.Agencia;

public class JPAAgenciaDAO extends JPAGenericDAO<Agencia, Integer> implements
		AgenciaDAO {

	public JPAAgenciaDAO() {
		super(Agencia.class);
	}

}
