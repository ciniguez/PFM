package pfm.jpa;

import java.util.List;

import javax.persistence.Query;

import pfm.dao.FacturaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Factura;
import pfm.entidades.Usuario;

public class JPAFacturaDAO extends JPAGenericDAO<Factura, Integer> implements
		FacturaDAO {

	public JPAFacturaDAO() {
		super(Factura.class);
	}

	@Override
	public List<Factura> getFacturasByAgencia(Agencia agencia, boolean pagado,
			boolean pendiente) {
		Query query = em.createNamedQuery("getFacturasByAgencia");
		query.setParameter("agencia", agencia);
		query.setParameter("pagado", pagado);
		query.setParameter("pendiente", pendiente);
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}

	@Override
	public List<Factura> getFacturasPendientesByCliente(Usuario cliente) {
		Query query = em.createNamedQuery("getFacturasPendientesByCliente");
		query.setParameter("cliente", cliente);
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}
}
