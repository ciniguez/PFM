package pfm.jpa;

import java.util.List;

import javax.persistence.Query;

import pfm.dao.FacturaDetalleDAO;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;

public class JPAFacturaDetalleDAO extends
		JPAGenericDAO<FacturaDetalle, Integer> implements FacturaDetalleDAO {

	public JPAFacturaDetalleDAO() {
		super(FacturaDetalle.class);
	}

	@Override
	public List<FacturaDetalle> getFacturaDetalleByFactura(Factura factura,
			boolean eliminado) {
		Query query = em.createNamedQuery("getFacturaDetalleByFactura");
		query.setParameter("factura", factura);
		query.setParameter("eliminado", eliminado);
		@SuppressWarnings("unchecked")
		List<FacturaDetalle> resultado = query.getResultList();
		return resultado;

	}

	@Override
	public void setPrecioByBodegaDetalle(FacturaDetalle facturaDetalle,
			double precio) {

		facturaDetalle.setPrecio(precio);
	}

	@Override
	public void setTotalesFacturaDetalle(FacturaDetalle facturaDetalle,
			double valorDescuento, double valorIva) {

		double descuento = 0;
		double iva = 0;
		double subtotal = 0;
		double total = 0;

		subtotal = Math.round((facturaDetalle.getCantidad() * facturaDetalle
				.getPrecio()) * 100.0) / 100.0;
		descuento = Math.round(((subtotal * valorDescuento) / 100) * 100.0) / 100.0;
		iva = Math.round(((subtotal * valorIva) / 100) * 100.0) / 100.0;
		total = Math.round((subtotal - descuento + iva) * 100.0) / 100.0;
		facturaDetalle.setDescuento(descuento);
		facturaDetalle.setIva(iva);
		facturaDetalle.setSubtotal(subtotal);
		facturaDetalle.setTotal(total);

	}

}
