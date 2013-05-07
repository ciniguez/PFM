package pfm.beans.rest;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pfm.entidades.Agencia;
import pfm.entidades.Descuento;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.MedioDePago;
import pfm.entidades.Usuario;
import pfm.entidadesRest.CarroCompras;
import pfm.entidadesRest.ItemProducto;
import pfm.jpa.JPADAOFactory;

/**
 * Esta clase representa un SERVICIO WEB, que tiene como funcion exponer metodos
 * en la internet a fin de que sean consumidos por los clientes (otras
 * aplicaciones).
 * 
 * @author Carlos Iniguez
 * @version 1.0
 */
@Path("/compra/")
public class CompraResource {

	/**
	 * Obtener el listado de productos en el Carro de Compras
	 * 
	 * @param id
	 *            Identificador de la factura.
	 * @return Archivo JSON del Usuario, XML o Texto Plano (depende de que
	 *         acepte el cliente).
	 */
	@GET
	@Path("/carro/{idFactura}")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ "application/json" })
	public List<ItemProducto> getCarroCompraActual(
			@PathParam("idFactura") int idFactura) {
		List<ItemProducto> listaProductos = new ArrayList<ItemProducto>();
		if (idFactura == 0) {
			return listaProductos;
		}
		Factura factura = JPADAOFactory.getFactory().getFacturaDAO()
				.read(idFactura);

		if (factura != null) {
			listaProductos = new ArrayList<ItemProducto>();
			List<FacturaDetalle> detalles = JPADAOFactory.getFactory()
					.getFacturaDetalleDAO()
					.getFacturaDetalleByFactura(factura, false);

			for (FacturaDetalle facturaDetalle : detalles) {
				ItemProducto itemProducto = new ItemProducto();
				itemProducto.setCantidad(facturaDetalle.getCantidad());
				itemProducto.setDescuento(facturaDetalle.getDescuento());
				itemProducto.setDescuentoTotal(factura.getDescuento());
				itemProducto.setIdBodegaDetalle(facturaDetalle
						.getBodegaDetalle().getId());
				itemProducto.setIdFacturaDetalle(facturaDetalle.getId());
				itemProducto.setNombreProducto(facturaDetalle
						.getBodegaDetalle().getProducto().getNombre());
				itemProducto.setPrecio(facturaDetalle.getPrecio());
				itemProducto.setSubtotal(facturaDetalle.getSubtotal());
				itemProducto.setTotalFactura(factura.getTotal());

				listaProductos.add(itemProducto);
			}
			return listaProductos;
		}
		return listaProductos;

	}

	/**
	 * Confirmar pedido de Carro de Compras Actualiza el estado de la factura a
	 * 
	 * @param user
	 *            Objeto Usuario a actualizar
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
	public List<CarroCompras> getCarrosCompra(
			@PathParam("idUsuario") int idUsuario,
			@PathParam("idAgencia") int idAgencia) {
		List<CarroCompras> listaCarros = new ArrayList<CarroCompras>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO()
				.read(idUsuario);
		Agencia agencia = JPADAOFactory.getFactory().getAgenciaDAO()
				.read(idAgencia);
		List<Factura> facturas = JPADAOFactory.getFactory().getFacturaDAO()
				.getFacturasPendientesByClienteAndAgencia(cliente, agencia);
		if (facturas != null) {
			for (Factura factura : facturas) {
				CarroCompras carro = new CarroCompras();
				carro.setFechaCreacion(df.format(factura.getFecha()));
				carro.setIdFactura(factura.getId());
				carro.setNombreAgencia(factura.getAgencia().getNombre());
				carro.setTotal(factura.getTotal());
				listaCarros.add(carro);
			}
		}
		return listaCarros;
	}

	/**
	 * Eliminado logico de la factura por Id
	 * 
	 * @author Gabus
	 * @param id
	 * @return entidad factura
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response EliminarFactura(@PathParam("id") int id)
			throws URISyntaxException {
		Factura factura = new Factura();
		factura = JPADAOFactory.getFactory().getFacturaDAO().read(id);
		factura.setEliminado(true);
		factura.setPendiente(false);		
		JPADAOFactory.getFactory().getFacturaDAO().update(factura);

		return Response.ok(factura).build();

	}

	/**
	 * Vuelve a generar los totales en facturaDetalle y factura, para confirmar
	 * los totales
	 * 
	 * @author Gabus
	 * @param id
	 * @param totalFactura
	 * @return entidad factura
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/confirmaTotal/{id}/{totalFactura}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ConfirmarTotal(@PathParam("id") int id,
			@PathParam("totalFactura") double totalFactura)
			throws URISyntaxException {

		double valorDescuento;
		double valorIva;
		// obtiene la factura por id
		Factura factura = new Factura();
		factura = JPADAOFactory.getFactory().getFacturaDAO().read(id);
		// obtiene el listado de facturaDetalle
		List<FacturaDetalle> facturasDetalle = new ArrayList<FacturaDetalle>();
		facturasDetalle = JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.getFacturaDetalleByFactura(factura, false);
		// recorre la factura detalle actualizando los totales
		for (FacturaDetalle f : facturasDetalle) {
			// obtiene el descuento del producto
			Descuento descuento = new Descuento();
			int idDescuento = JPADAOFactory.getFactory()
					.getDescuentoProductoDAO()
					.getDescuentoId(f.getBodegaDetalle().getProducto(), false);
			if (idDescuento != 0) {
				descuento = JPADAOFactory.getFactory().getDescuentoDAO()
						.getValorDescuentoByFecha(idDescuento, false);
				if (descuento != null) {
					valorDescuento = descuento.getValor();
				} else {
					valorDescuento = 0;
				}
			} else {
				valorDescuento = 0;
			}
			// obtiene el iva de la empresa
			valorIva = JPADAOFactory
					.getFactory()
					.getEmpresaDAO()
					.getIvaByEmpresa(
							f.getBodegaDetalle().getBodega().getAgencia()
									.getEmpresa().getId(), false);
			// actualiza el precio
			f.setPrecio(JPADAOFactory
					.getFactory()
					.getBodegaDetalleDAO()
					.getPrecioByBodegaDetalle(f.getBodegaDetalle().getId(),
							false));
			// setea los totales
			JPADAOFactory.getFactory().getFacturaDetalleDAO()
					.setTotalesFacturaDetalle(f, valorDescuento, valorIva);
			// actualiza el detalle
			JPADAOFactory.getFactory().getFacturaDetalleDAO().update(f);
		}
		// obtiene nuevamente el listado de facturaDetalle
		facturasDetalle = new ArrayList<FacturaDetalle>();
		facturasDetalle = JPADAOFactory.getFactory().getFacturaDetalleDAO()
				.getFacturaDetalleByFactura(factura, false);
		// sete el total de la factura
		JPADAOFactory.getFactory().getFacturaDAO()
				.setTotalesFactura(factura, facturasDetalle);
		// actualiza la factura
		JPADAOFactory.getFactory().getFacturaDAO().update(factura);

		return Response.ok(factura).build();
	}

	/**
	 * Confirma la compra desde el movil
	 * 
	 * @author Gabus
	 * @param idFactura
	 * @param idMedioDePago
	 * @return entidad factura
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/confirmaCompra/{idFactura}/{idMedioDePago}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ConfirmarCompra(@PathParam("idFactura") int idFactura,
			@PathParam("idMedioDePago") int idMedioDePago)
			throws URISyntaxException {

		Factura factura = new Factura();
		factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);
		MedioDePago medioDePago = new MedioDePago();
		medioDePago = JPADAOFactory.getFactory().getMedioPagoDAO()
				.read(idMedioDePago);
		// setea la factura en modo confirmar
		factura.setPagado(true);
		factura.setMedioDePago(medioDePago);
		factura.setPendiente(false);
		// actualiza la factura
		JPADAOFactory.getFactory().getFacturaDAO().update(factura);

		return Response.ok(factura).build();
	}

}