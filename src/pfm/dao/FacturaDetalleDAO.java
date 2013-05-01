package pfm.dao;

import java.util.List;

import pfm.entidades.BodegaDetalle;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;

public interface FacturaDetalleDAO extends GenericDAO<FacturaDetalle, Integer> {

	public List<FacturaDetalle> getFacturaDetalleByFactura(Factura factura,
			boolean eliminado);
	
	public FacturaDetalle getFacturaDetalleByBodDetAndFac(Factura factura,BodegaDetalle bodegaDetalle);

	public void setPrecioByBodegaDetalle(FacturaDetalle facturaDetalle,
			double precio);

	public void setTotalesFacturaDetalle(FacturaDetalle facturaDetalle,
			double valorDescuento, double valorIva);
}
