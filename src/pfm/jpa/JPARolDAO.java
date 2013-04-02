package pfm.jpa;

import pfm.dao.RolDAO;
import pfm.entidades.Rol;

public class JPARolDAO extends JPAGenericDAO<Rol, Integer> implements RolDAO {

	public JPARolDAO() {
		super(Rol.class);
	}

}
