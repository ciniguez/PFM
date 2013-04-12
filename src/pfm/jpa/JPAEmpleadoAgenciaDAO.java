package pfm.jpa;

import pfm.dao.EmpleadoAgenciaDAO;
import pfm.entidades.EmpleadoAgencia;

public class JPAEmpleadoAgenciaDAO extends
		JPAGenericDAO<EmpleadoAgencia, Integer> implements EmpleadoAgenciaDAO {

	public JPAEmpleadoAgenciaDAO() {
		super(EmpleadoAgencia.class);
	}
}
