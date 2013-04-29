package pfm.beans.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pfm.entidades.Agencia;
import pfm.jpa.JPADAOFactory;

/**
 * 
 * @author Gabus
 * 
 */
@Path("/agencia/")
public class AgenciaResource {

	/**
	 * Listado de agencias para que seleccione al momento de realizar el login
	 * en la aplicacion movil
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/listAgencias")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAgencias() throws URISyntaxException {

		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		List<Agencia> agencias = JPADAOFactory.getFactory().getAgenciaDAO()
				.find(attributes, values, order, index, size);

		if (agencias != null) {
			GenericEntity<List<Agencia>> entity = new GenericEntity<List<Agencia>>(
					agencias) {
			};
			return Response.ok(entity).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: ").build();
		}

	}

}
