package pfm.beans.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pfm.entidades.MedioDePago;
import pfm.jpa.JPADAOFactory;

/**
 * Servicios REST de la entidad Medio de Pago
 * 
 * @author Gabus
 * 
 */
@Path("/medioDePago/")
public class MedioDePagoResource {
	/**
	 * Listado de los medios de pago, para el momento de confirmar la compra
	 * 
	 * @return Response(MedioPago)
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/listMedioPago")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAgencias() throws URISyntaxException {

		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		List<MedioDePago> medioDePagos = JPADAOFactory.getFactory()
				.getMedioPagoDAO().find(attributes, values, order, index, size);

		if (medioDePagos != null) {
			GenericEntity<List<MedioDePago>> entity = new GenericEntity<List<MedioDePago>>(
					medioDePagos) {
			};
			return Response.ok(entity).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: ").build();
		}

	}
}
