package pfm.dao;

import java.util.List;

import pfm.entidades.Bodega;
import pfm.entidades.BodegaDetalle;
import pfm.entidades.Producto;

public interface BodegaDetalleDAO extends GenericDAO<BodegaDetalle, Integer> {

	public List<BodegaDetalle> getBodegaDetalleByBodegaAndProducto(
			Producto producto, Bodega bodega);
}
