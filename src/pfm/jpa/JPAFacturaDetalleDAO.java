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
	public List<FacturaDetalle> getFacturaDetalleByFactura(Factura factura) {
		Query query = em.createNamedQuery("getFacturaDetalleByFactura");
		query.setParameter("factura", factura);
		@SuppressWarnings("unchecked")
		List<FacturaDetalle> resultado = query.getResultList();
		return resultado;

	}

	@Override
	public void setTotalesFacturaDetalle(FacturaDetalle facturaDetalle,
			double valorDescuento, double valorIva) {

		double precioTotal = 0;
		double descuento = 0;
		double iva = 0;
		precioTotal = Math.round((facturaDetalle.getCantidad() * facturaDetalle
				.getPrecio()) * 100.0) / 100.0;
		descuento = Math.round(((precioTotal * valorDescuento) / 100) * 100.0) / 100.0;
		iva = Math.round(((precioTotal * valorIva) / 100) * 100.0) / 100.0;
		facturaDetalle.setDescuento(descuento);
		facturaDetalle.setIva(iva);
		facturaDetalle.setSubtotal(precioTotal - descuento);
		facturaDetalle.setTotal(precioTotal - descuento + iva);

	}

	@Override
	public void setPrecioByBodegaDetalle(FacturaDetalle facturaDetalle,
			double precio) {

		facturaDetalle.setPrecio(precio);
	}
}
