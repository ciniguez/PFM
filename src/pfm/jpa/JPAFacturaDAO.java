package pfm.jpa;

import java.util.List;

import javax.persistence.Query;

import pfm.dao.FacturaDAO;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Factura;

public class JPAFacturaDAO extends JPAGenericDAO<Factura, Integer> implements
		FacturaDAO {

	public JPAFacturaDAO() {
		super(Factura.class);
	}

	@Override
	public List<Factura> getFacturasByEmpleado(EmpleadoAgencia empleadoAgencia) {
		Query query = em.createNamedQuery("getFacturasByEmpleado");
		query.setParameter("empleadoAgencia", empleadoAgencia);
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}

}
