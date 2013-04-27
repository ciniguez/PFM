package pfm.beans.rest;

import java.net.URISyntaxException;
import java.util.List;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

/**
 * Esta clase representa un SERVICIO WEB, que tiene como funcion exponer metodos
 * en la internet a fin de que sean consumidos por los clientes (otras
 * aplicaciones).
 * 
 * @author Carlos Iniguez
 * @version 1.0
 */
@Path("/autenticacion/")
public class AutenticacionResource {

	// TODO: Hacer las consultas con inyeccion de dependencias
	/*
	 * @ManagedProperty(value = "#{DAOFactory.usuarioDAO}") private UsuarioDAO
	 * usuarioDAO;
	 * 
	 * @ManagedProperty(value = "#{DAOFactory.rolDAO}") private RolDAO rolDAO;
	 */
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUsuarioXML(Usuario user) throws URISyntaxException {
		// TODO: quitar el rol quemado a 2
		user.setRol(JPADAOFactory.getFactory().getRolDAO().read(2));
		JPADAOFactory.getFactory().getUsuarioDAO().create(user);
		return Response.status(204).entity("1").build();
	}

	/**
	 * Login de un usuario cliente al sistema.
	 * 
	 * @param username
	 *            Nombre del Usuario para acceso al sistema
	 * @param clave
	 *            Clave de acceso al sistema
	 * @return
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/login/{username}/{clave}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginXML(@PathParam("username") String username,
			@PathParam("clave") String clave) throws URISyntaxException {
		Usuario usuario = null;
		String[] attributes = { "eliminado", "username", "password" };
		String[] values = { "0", username, clave };
		String order = "id";
		int index = -1;
		int size = -1;
		List<Usuario> usuarios = JPADAOFactory.getFactory().getUsuarioDAO()
				.find(attributes, values, order, index, size);
		if (usuarios.size() == 1) {
			usuario = usuarios.get(0);
		}

		// TODO No poner el id de Rol quemado en 2 sino ponerlo en sesion total
		// del sistema.
		if (usuario != null && usuario.getRol().getId() == 2) {
			// TODO Investigar la devolucion del Response.status
			return Response.ok(usuario).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: " + username).build();
		}
	}

}