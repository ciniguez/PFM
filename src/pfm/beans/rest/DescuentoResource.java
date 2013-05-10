package pfm.beans.rest;

import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pfm.entidades.Descuento;
import pfm.entidades.Producto;
import pfm.jpa.JPADAOFactory;

/**
 * Web service para obtener datos de los descuentos por productos
 * 
 * @author Gabus
 * 
 */
@Path("/descuento/")
public class DescuentoResource {
	/**
	 * Obtiene el descuento por medio del producto
	 * 
	 * @param idProducto
	 * @return Response(Descuento)
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/getDescuentoByProducto/{idProducto}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDescuentoByProducto(
			@PathParam("idProducto") int idProducto) throws URISyntaxException {
		Producto producto = new Producto();
		producto = JPADAOFactory.getFactory().getProductoDAO().read(idProducto);
		int idDescuento = JPADAOFactory.getFactory().getDescuentoProductoDAO()
				.getDescuentoId(producto, false);

		Descuento descuento = JPADAOFactory.getFactory().getDescuentoDAO()
				.getValorDescuentoByFecha(idDescuento, false);

		if (descuento != null)
			return Response.ok(descuento).build();
		else
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: " + idProducto).build();

	}
}
