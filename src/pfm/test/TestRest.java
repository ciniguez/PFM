package pfm.test;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import pfm.entidades.BodegaDetalle;
import pfm.entidades.Empresa;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Producto;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

public class TestRest {

	public static Set<FacturaDetalle> listarProductosCuentaXML(int idUsuario) {
		Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
		List<Factura> facturas = JPADAOFactory.getFactory().getFacturaDAO().getFacturasPendientesByCliente(cliente);

		if (facturas.size() == 1) {
			System.out.println("Existe un carro de compras on id: " + facturas.get(0).getId());
			return facturas.get(0).getFacturaDetalle();
		} else {
			return null;
		}
	}

	public static void addProductoXML(int idBodegaDetalle, int idUsuario, int cantidad) {
		Factura factura;
		Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
		List<Factura> facturas = JPADAOFactory.getFactory().getFacturaDAO().getFacturasPendientesByCliente(cliente);
		BodegaDetalle bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO().read(idBodegaDetalle);
		Producto producto = bodegaDetalle.getProducto();
		Empresa empresa = bodegaDetalle.getBodega().getAgencia().getEmpresa();
		int idDescuento = JPADAOFactory.getFactory().getDescuentoProductoDAO().getDescuentoId(producto, false);
		double valorDescuento = JPADAOFactory.getFactory().getDescuentoDAO().getValorDescuentoByFecha(idDescuento, false);
		double subtotal = (cantidad * bodegaDetalle.getPrecio()) - valorDescuento;

		//Si no existe una factura que emula como carro de compras, creamos una.
		if (facturas.size() == 0) {
			factura = new Factura();
			factura.setAgencia(bodegaDetalle.getBodega().getAgencia());
			factura.setCliente(cliente);
			factura.setDescuento(factura.getDescuento() + valorDescuento);
			//TODO: poner en una enumeracion el valor Eliminado
			factura.setEliminado(false);
			factura.setEmpleadoAgencia(null);
			factura.setFecha(new Date());
			factura.setIva(empresa.getIva());
			factura.setMedioDePago(null);
			factura.setPagado(false);
			factura.setPendiente(true);
			factura.setSubtotal(subtotal);
			factura.setTotal(factura.getSubtotal() + subtotal);
			JPADAOFactory.getFactory().getFacturaDAO().create(factura);
		} else {
			factura = facturas.get(0);
		}

		//Creación de la Factura Detalle
		FacturaDetalle facturaDetalle = new FacturaDetalle();
		facturaDetalle.setBodegaDetalle(bodegaDetalle);
		facturaDetalle.setCantidad(cantidad);
		facturaDetalle.setDescuento(valorDescuento);
		facturaDetalle.setEliminado(false);
		facturaDetalle.setFactura(factura);
		facturaDetalle.setIva(empresa.getIva());
		facturaDetalle.setPrecio(bodegaDetalle.getPrecio());
		facturaDetalle.setSubtotal(subtotal);
		facturaDetalle.setTotal(subtotal);
		JPADAOFactory.getFactory().getFacturaDetalleDAO().create(facturaDetalle);

		//Disminuyo Stock.
		bodegaDetalle.setCantidad(bodegaDetalle.getCantidad() - cantidad);
		JPADAOFactory.getFactory().getBodegaDetalleDAO().update(bodegaDetalle);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		Set<FacturaDetalle> productos = listarProductosCuentaXML(2);
		if (productos != null) {
			for (FacturaDetalle facturaDetalle : productos) {
				System.out.println(facturaDetalle.getId());
			}
		}else{
			System.out.println("No existen productos");
		}
		*/
		
		addProductoXML(2, 2, 2);

	}

}
