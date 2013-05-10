package pfm.beans.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pfm.entidades.BodegaDetalle;
import pfm.entidades.Descuento;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.jpa.JPADAOFactory;

/**
 * 
 * @author Gabus
 * 
 */
@Path("/facturaDetalle/")
public class FacturaDetalleResource {

	/**
	 * Obtener el listado de productos en el Carro de Compras
	 * 
	 * @param id
	 *            Identificador de la factura.
	 * @return listado de objetos JSON de facturaDetalle
	 */
	@GET
	@Path("/carroCompraActual/{idFactura}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<FacturaDetalle> getCarroCompraActual(
			@PathParam("idFactura") int idFactura) {

		Factura factura = JPADAOFactory.getFactory().getFacturaDAO()
				.read(idFactura);
		List<FacturaDetalle> listaFacturaDetalle = new ArrayList<FacturaDetalle>();
		if (factura != null) {
			listaFacturaDetalle = JPADAOFactory.getFactory()
					.getFacturaDetalleDAO()
					.getFacturaDetalleByFactura(factura, false);
		}

		return listaFacturaDetalle;

	}

	/**
	 * Obtiene la facturaDetalle por medio del id
	 * 
	 * @param id
	 * @return Response(FacturaDetalle)
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/getFacturaDetalleById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFacturaDetalleById(@PathParam("id") int id)
			throws URISyntaxException {
		FacturaDetalle facturaDetalle = new FacturaDetalle();
		facturaDetalle = JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.read(id);

		if (facturaDetalle != null)
			return Response.ok(facturaDetalle).build();
		else
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: " + id).build();
	}

	/**
	 * Verifica si el producto ya exite en otra FacturaDetalle de la misma
	 * Factura
	 * 
	 * @param idFactura
	 * @param idBodegaDetalle
	 * @return Response(FacturaDetaklle)
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/existeProductoByFacturaDetalle/{idFactura}/{idBodegaDetalle}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response existeProductoByFacturaDetalle(
			@PathParam("idFactura") int idFactura,
			@PathParam("idBodegaDetalle") int idBodegaDetalle)
			throws URISyntaxException {
		Factura factura = new Factura();
		factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);
		BodegaDetalle bodegaDetalle = new BodegaDetalle();
		bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO()
				.read(idBodegaDetalle);

		FacturaDetalle facturaDetalle = new FacturaDetalle();
		facturaDetalle = JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.getFacturaDetalleByBodDetAndFac(factura, bodegaDetalle);

		if (facturaDetalle != null)
			return Response.ok(facturaDetalle).build();
		else
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: " + idFactura).build();
	}

	/**
	 * Agrega un nuevo producto a la FacturaDetalle, y si no tiene Factura
	 * genera una nueva
	 * 
	 * @param idFactura
	 * @param idAgencia
	 * @param idCliente
	 * @param idBodegaDetalle
	 * @param idDescuento
	 * @param cantidad
	 * @return Response(Factura)
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/create/{idFactura}/{idAgencia}/{idCliente}/{idBodegaDetalle}/{idDescuento}/{cantidad}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response aniadirProducto(@PathParam("idFactura") int idFactura,
			@PathParam("idAgencia") int idAgencia,
			@PathParam("idCliente") int idCliente,
			@PathParam("idBodegaDetalle") int idBodegaDetalle,
			@PathParam("idDescuento") int idDescuento,
			@PathParam("cantidad") int cantidad) throws URISyntaxException {

		Factura factura = new Factura();
		// si el idFactura == 0 : crear una nueva
		if (idFactura == 0) {

			factura.setAgencia(JPADAOFactory.getFactory().getAgenciaDAO()
					.read(idAgencia));
			factura.setCliente(JPADAOFactory.getFactory().getUsuarioDAO()
					.read(idCliente));
			factura.setEliminado(false);
			factura.setEmpleadoAgencia(null);
			factura.setFecha(new Date());
			factura.setMedioDePago(null);
			factura.setPagado(false);
			factura.setPendiente(true);
			factura.setSubtotal(0);
			factura.setDescuento(0);
			factura.setIva(0);
			factura.setTotal(0);
			// crea la nuevaFactura
			JPADAOFactory.getFactory().getFacturaDAO().create(factura);
		} else {
			factura = JPADAOFactory.getFactory().getFacturaDAO()
					.read(idFactura);
		}

		BodegaDetalle bodegaDetalle = new BodegaDetalle();
		bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO()
				.read(idBodegaDetalle);
		Descuento descuento = new Descuento();
		descuento = JPADAOFactory.getFactory().getDescuentoDAO()
				.getValorDescuentoByFecha(idDescuento, false);
		double valorDescuento = 0.00;
		if (descuento != null) {
			valorDescuento = descuento.getValor();
		}
		double valorIva = JPADAOFactory
				.getFactory()
				.getEmpresaDAO()
				.getIvaByEmpresa(
						bodegaDetalle.getBodega().getAgencia().getEmpresa()
								.getId(), false);
		// genera facturaDetalle
		FacturaDetalle facturaDetalle = new FacturaDetalle();
		facturaDetalle.setFactura(factura);
		facturaDetalle.setBodegaDetalle(bodegaDetalle);
		facturaDetalle.setCantidad(cantidad);
		facturaDetalle.setPrecio(bodegaDetalle.getPrecio());
		facturaDetalle.setSubtotal(0);
		facturaDetalle.setDescuento(0);
		facturaDetalle.setIva(0);
		facturaDetalle.setTotal(0);
		facturaDetalle.setEliminado(false);
		// setea los totales de la facturaDetalle
		JPADAOFactory
				.getFactory()
				.getFacturaDetalleDAO()
				.setTotalesFacturaDetalle(facturaDetalle, valorDescuento,
						valorIva);
		// crear facturaDetalle
		JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.create(facturaDetalle);

		// obtiene el listado de facturaDetalle
		List<FacturaDetalle> listaFacturaDetalle = JPADAOFactory.getFactory()
				.getFacturaDetalleDAO()
				.getFacturaDetalleByFactura(factura, false);
		// setea los totales de la factura
		JPADAOFactory.getFactory().getFacturaDAO()
				.setTotalesFactura(factura, listaFacturaDetalle);
		// actualiza la factura
		JPADAOFactory.getFactory().getFacturaDAO().update(factura);

		if (factura != null)
			return Response.ok(factura).build();
		else
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Entity not found for UUID: " + idFactura).build();
	}

	/**
	 * Actualiza la cantidad y totales en FacturaDetalle y los totales en
	 * Factura
	 * 
	 * @param idFacturaDetalle
	 * @param idDescuento
	 * @param cantidad
	 * @return Response(Factura)
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/update/{idFacturaDetalle}/{idDescuento}/{cantidad}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizarProducto(
			@PathParam("idFacturaDetalle") int idFacturaDetalle,
			@PathParam("idDescuento") int idDescuento,
			@PathParam("cantidad") int cantidad) throws URISyntaxException {

		FacturaDetalle facturaDetalle = new FacturaDetalle();
		Factura factura = new Factura();
		facturaDetalle = JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.read(idFacturaDetalle);

		if (cantidad != facturaDetalle.getCantidad()) {
			facturaDetalle.setCantidad(cantidad);
			facturaDetalle.setPrecio(facturaDetalle.getBodegaDetalle()
					.getPrecio());

			Descuento descuento = new Descuento();
			descuento = JPADAOFactory.getFactory().getDescuentoDAO()
					.getValorDescuentoByFecha(idDescuento, false);
			double valorDescuento = 0.00;
			if (descuento != null) {
				valorDescuento = descuento.getValor();
			}
			double valorIva = JPADAOFactory
					.getFactory()
					.getEmpresaDAO()
					.getIvaByEmpresa(
							facturaDetalle.getBodegaDetalle().getBodega()
									.getAgencia().getEmpresa().getId(), false);
			// setea los totales de la facturaDetalle
			JPADAOFactory
					.getFactory()
					.getFacturaDetalleDAO()
					.setTotalesFacturaDetalle(facturaDetalle, valorDescuento,
							valorIva);
			// actualiza facturaDetalle
			JPADAOFactory.getFactory().getFacturaDetalleDAO()
					.update(facturaDetalle);
			// obtiene la factura
			factura = facturaDetalle.getFactura();
			// obtiene el listado de facturaDetalle
			List<FacturaDetalle> listaFacturaDetalle = JPADAOFactory
					.getFactory().getFacturaDetalleDAO()
					.getFacturaDetalleByFactura(factura, false);
			// setea los totales de la factura
			JPADAOFactory.getFactory().getFacturaDAO()
					.setTotalesFactura(factura, listaFacturaDetalle);
			// actualiza la factura
			JPADAOFactory.getFactory().getFacturaDAO().update(factura);

		}

		return Response.ok(factura).build();

	}

	/**
	 * Elimina fisicamente la FacturaDetalle
	 * 
	 * @param idFacturaDetalle
	 * @return Response(Factura)
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/delete/{idFacturaDetalle}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarPoducto(
			@PathParam("idFacturaDetalle") int idFacturaDetalle)
			throws URISyntaxException {

		FacturaDetalle facturaDetalle = new FacturaDetalle();
		Factura factura = new Factura();

		facturaDetalle = JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.read(idFacturaDetalle);
		factura = facturaDetalle.getFactura();
		// elimina facturaDetalle By id
		JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.deleteByID(idFacturaDetalle);

		// obtiene el listado de facturaDetalle
		List<FacturaDetalle> listaFacturaDetalle = JPADAOFactory.getFactory()
				.getFacturaDetalleDAO()
				.getFacturaDetalleByFactura(factura, false);
		// setea los totales de la factura
		JPADAOFactory.getFactory().getFacturaDAO()
				.setTotalesFactura(factura, listaFacturaDetalle);
		// actualiza la factura
		JPADAOFactory.getFactory().getFacturaDAO().update(factura);
		return Response.ok(factura).build();
	}
}
