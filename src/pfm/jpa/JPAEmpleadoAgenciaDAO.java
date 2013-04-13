package pfm.jpa;

import javax.persistence.Query;

import pfm.dao.EmpleadoAgenciaDAO;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Usuario;

public class JPAEmpleadoAgenciaDAO extends
		JPAGenericDAO<EmpleadoAgencia, Integer> implements EmpleadoAgenciaDAO {

	public JPAEmpleadoAgenciaDAO() {
		super(EmpleadoAgencia.class);
	}

	@Override
	public EmpleadoAgencia getAgenciaByEmpleado(Usuario empleado) {
		Query query = em.createNamedQuery("getAgenciaByEmpleado");
		query.setParameter("empleado", empleado);
		EmpleadoAgencia resultado = (EmpleadoAgencia) query.getSingleResult();
		return resultado;
	}
}
