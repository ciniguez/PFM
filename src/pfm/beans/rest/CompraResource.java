package pfm.beans.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pfm.entidades.BodegaDetalle;
import pfm.entidades.Empresa;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Producto;
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
	@Path("/{idusuario}")
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML })
	public Response listarProductosCuentaXML(@PathParam("id") int idUsuario) {
		Set<FacturaDetalle> productos = null;
		String[] attributes = { "eliminado", "clienteId", "pendiente" };
		String[] values = { "0", String.valueOf(idUsuario), "1" };
		String order = "id";
		int index = -1;
		int size = -1;
		List<Factura> carrosCompras = JPADAOFactory.getFactory().getFacturaDAO().find(attributes, values, order, index, size);

		if (carrosCompras.size() == 1) {
			Factura carroCompras = carrosCompras.get(0);
			productos = carroCompras.getFacturaDetalle();
		}

		if (productos != null) {
			//TODO Investigar la devolucion del Response.status
			return Response.ok(productos).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for UUID: " + idUsuario).build();
		}
	}

	/**
	 * Agregar Producto a Carro de Compras
	 * @param idBodegaDetalle
	 * @return
	 * @throws URISyntaxException
	 */
	@POST
	@Path("/add/{idbodegadetalle}/{idfactura}/{cantidad}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML})
	public Response addProductoXML(int idBodegaDetalle,  int idFactura,int idUsuario, int cantidad) throws URISyntaxException {
		
		/*Factura factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);
		//Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
		//List<Factura> facturas = JPADAOFactory.getFactory().getFacturaDAO().getFacturasPendientesByCliente(cliente);
		BodegaDetalle bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO().read(idBodegaDetalle);
		Producto producto = bodegaDetalle.getProducto();
		Empresa empresa = bodegaDetalle.getBodega().getAgencia().getEmpresa();
		int idDescuento = JPADAOFactory.getFactory().getDescuentoProductoDAO().getDescuentoId(producto, false);
		double valorDescuento = JPADAOFactory.getFactory().getDescuentoDAO().getValorDescuentoByFecha(idDescuento, false);
		double subtotal= (cantidad * bodegaDetalle.getPrecio())-valorDescuento;

		
		//Creacion de la Factura Detalle
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
		bodegaDetalle.setCantidad(bodegaDetalle.getCantidad()-cantidad);
		JPADAOFactory.getFactory().getBodegaDetalleDAO().update(bodegaDetalle);
		*/
		return Response.status(Response.Status.ACCEPTED).build();
		
	}

	/**
	 * Confirmar pedido de Carro de Compras
	 * Actualiza el estado de la factura a 
	 * @param user Objeto Usuario a actualizar
	 * @return String Mensaje de Servidor
	 */
	@PUT
	@Path("/enviar")
	@Consumes(MediaType.APPLICATION_XML)
	public void confirmarComprasXML() {

	}

	/**
	 * Eliminar un Usuario dado su ID. Retorna mensaje de Servidor
	 * @param id Identificador del Usurio que se requiere eliminar.
	 * @return String Mensaje de Servidor.
	 */
	@DELETE
	@Path("/delete/{id}")
	public Response eliminarProducto(@PathParam("id") Integer id) {

		return Response.status(204).entity("1").build();
	}
}