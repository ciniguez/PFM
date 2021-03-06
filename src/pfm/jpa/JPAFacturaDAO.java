package pfm.jpa;

import java.util.List;

import javax.persistence.Query;

import pfm.dao.FacturaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Usuario;

public class JPAFacturaDAO extends JPAGenericDAO<Factura, Integer> implements
		FacturaDAO {

	public JPAFacturaDAO() {
		super(Factura.class);
	}

	@Override
	public List<Factura> getFacturasPendientesByAgencia(Agencia agencia, boolean pendiente) {
		Query query = em.createNamedQuery("getFacturasPendientesByAgencia");
		query.setParameter("agencia", agencia);
		query.setParameter("pendiente", pendiente);
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}

	@Override
	public List<Factura> getFacturasPendientes(boolean pendiente) {
		Query query = em.createNamedQuery("getFacturasPendientes");
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

	@Override
	public List<Factura> getFacturasPendientesByClienteAndAgencia(
			Usuario cliente, Agencia agencia) {
		Query query = em
				.createNamedQuery("getFacturasPendientesByClienteAndAgencia");
		query.setParameter("cliente", cliente);
		query.setParameter("agencia", agencia);
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}

	@Override
	public void setTotalesFactura(Factura factura,
			List<FacturaDetalle> listaFacturaDetalle) {

		double descuento = 0;
		double iva = 0;
		double subtotal = 0;
		double total = 0;

		for (FacturaDetalle det : listaFacturaDetalle) {
			descuento = Math.round((descuento + det.getDescuento()) * 100.0) / 100.0;
			iva = Math.round((iva + det.getIva()) * 100.0) / 100.0;
			subtotal = Math.round((subtotal + det.getSubtotal()) * 100.0) / 100.0;
			total = Math.round((total + det.getTotal()) * 100.0) / 100.0;
		}

		factura.setDescuento(descuento);
		factura.setIva(iva);
		factura.setSubtotal(subtotal);
		factura.setTotal(total);
	}

	@Override
	public List<Factura> getFacturasGeneradasByEmpleado(Usuario empleado) {
		Query query = em.createNamedQuery("getFacturasGeneradasByEmpleado");
		query.setParameter("empleado", empleado);
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}

	@Override
	public List<Factura> getFacturasGeneradas() {
		Query query = em.createNamedQuery("getFacturasGeneradas");
		@SuppressWarnings("unchecked")
		List<Factura> resultado = query.getResultList();
		return resultado;
	}

}
