package pfm.jpa;

import java.util.List;

import javax.persistence.Query;

import pfm.dao.FacturaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Factura;

public class JPAFacturaDAO extends JPAGenericDAO<Factura, Integer> implements
		FacturaDAO {

	public JPAFacturaDAO() {
		super(Factura.class);
	}

	@Override
	public List<Factura> getFacturasByAgencia(Agencia agencia, boolean pagado) {
		Query query = em.createNamedQuery("getFacturasByAgencia");
		query.setParameter("agencia", agencia);
		query.setParameter("pagado", pagado);
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}

}
