package pfm.beans.rest;

import java.net.URISyntaxException;
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
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public void listarCarroComprasJSON(@PathParam("id") Integer id) {
		//TODO: programar listarCarroComprasJSON
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
	public Response addProductoXML(int idBodegaDetalle, int idFactura, int cantidad) throws URISyntaxException {
		/*
		 * FacturaDetalle detalle = new FacturaDetalle();
		 * detalle.setBodegaDetalle
		 * (JPADAOFactory.getFactory().getBodegaDetalleDAO
		 * ().read(idBodegaDetalle)); detalle.setCantidad(cantidad);
		 * detalle.setDescuento
		 * (JPADAOFactory.getFactory().getDescuentoProductoDAO().)
		 * 
		 * if (JPADAOFactory.getFactory().getUsuarioDAO().create(user)) return
		 * Response.status(204).entity("1").build(); else return
		 * Response.status(204).entity("-1").build();
		 */
		return Response.status(204).entity("1").build();
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