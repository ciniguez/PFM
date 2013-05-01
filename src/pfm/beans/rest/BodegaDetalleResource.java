package pfm.beans.rest;

import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pfm.entidades.BodegaDetalle;
import pfm.jpa.JPADAOFactory;

/**
 * Servicio web para obtener los datos de bodega detalle
 * 
 * @author Gabus
 * 
 */

@Path("/bodegaDetalle/")
public class BodegaDetalleResource {

	@GET
	@Path("/getBodegaDetalleById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBodegaDetalleById(@PathParam("id") int id)
			throws URISyntaxException {
		BodegaDetalle bodegaDetalle = new BodegaDetalle();
		bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO()
				.read(id);

		if (bodegaDetalle != null)
			return Response.ok(bodegaDetalle).build();
		else
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: " + id).build();
	}
}
