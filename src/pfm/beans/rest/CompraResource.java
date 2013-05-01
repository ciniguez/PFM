package pfm.beans.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pfm.entidades.Agencia;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Usuario;
import pfm.entidadesRest.CarroCompras;
import pfm.entidadesRest.ItemProducto;
import pfm.jpa.JPADAOFactory;

/**
 * Esta clase representa un SERVICIO WEB, que tiene como funcion exponer metodos
 * en la internet a fin de que sean consumidos por los clientes (otras aplicaciones).
 * @author Carlos Iniguez
 * @version 1.0
 */
@Path("/compra/")
public class CompraResource {

	/**
	 * Obtener el listado de productos en el Carro de Compras
	 * @param id Identificador de la factura.
	 * @return Archivo JSON del Usuario, XML o Texto Plano (depende de que acepte el cliente).
	 */
	@GET
	@Path("/carro/{idFactura}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ "application/json" })
	public List<ItemProducto> getCarroCompraActual(@PathParam("idFactura") int idFactura) {
		List<ItemProducto> listaProductos = new ArrayList<ItemProducto>();
		if (idFactura == -1) {
			return listaProductos;
		}
		Factura factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);
		if (factura != null) {
			listaProductos = new ArrayList<ItemProducto>();

			for (FacturaDetalle facturaDetalle : factura.getFacturaDetalle()) {
				ItemProducto itemProducto = new ItemProducto(facturaDetalle.getBodegaDetalle().getId(), facturaDetalle.getBodegaDetalle().getProducto()
						.getNombre(), facturaDetalle.getSubtotal(), 
						facturaDetalle.getCantidad(), 
						facturaDetalle.getPrecio(),
						facturaDetalle.getId());
				listaProductos.add(itemProducto);
			}
			return listaProductos;
		}
		return listaProductos;

	}

	/**
	 * Confirmar pedido de Carro de Compras
	 * Actualiza el estado de la factura a 
	 * @param user Objeto Usuario a actualizar
	 * @return String Mensaje de Servidor
	 */
	@PUT
	@Path("/cofirmar")
	@Consumes(MediaType.APPLICATION_XML)
	public void confirmarComprasXML() {

	}

	@GET
	@Path("/carros/{idUsuario}/{idAgencia}")
	@Consumes({ "application/json" })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<CarroCompras> getCarrosCompra(@PathParam("idUsuario") int idUsuario, @PathParam("idAgencia") int idAgencia) {
		List<CarroCompras> listaCarros = new ArrayList<CarroCompras>();

		Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
		Agencia agencia = JPADAOFactory.getFactory().getAgenciaDAO().read(idAgencia);
		List<Factura> facturas = JPADAOFactory.getFactory().getFacturaDAO().getFacturasPendientesByClienteAndAgencia(cliente, agencia);
		if (facturas != null) {
			for (Factura factura : facturas) {
				CarroCompras carro = new CarroCompras();
				carro.setFechaCreacion(factura.getFecha().toString());
				carro.setIdFactura(factura.getId());
				carro.setNombreAgencia(factura.getAgencia().getNombre());
				carro.setTotal(factura.getTotal());
				listaCarros.add(carro);
			}
		}
		return listaCarros;
	}

}