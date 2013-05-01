package pfm.test;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import pfm.entidades.Agencia;
import pfm.entidades.BodegaDetalle;
import pfm.entidades.Empresa;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Producto;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

public class TestRest {

	public static Usuario login(String username, String clave) {
		Usuario usuario = null;
		String[] attributes = { "eliminado", "username" };
		String[] values = { "0", username, clave };
		String order = "id";
		int index = -1;
		int size = -1;
		List<Usuario> usuarios = JPADAOFactory.getFactory().getUsuarioDAO().find(attributes, values, order, index, size);
		if (usuarios.size() == 1) {
			if (usuarios.get(0).getRol().getId() == 2)
				usuario = usuarios.get(0);
		}
		return usuario;

	}

	public static Set<FacturaDetalle> listarProductosCarro(int idFactura) {
		Set<FacturaDetalle> detalles = null;
		Factura factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);

		//Obtengo los detalles (productos) de la factura
		if (factura != null) {
			System.out.println("Este es el listado de Productos (" + factura.getFacturaDetalle().size() + ")");
			detalles = factura.getFacturaDetalle();
		} else
			System.out.println("No existe Factura ni productos");
		return detalles;
	}

	public static List<Factura> listarCarrosCompra(int idUsuario, int idAgencia) {
		Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
		Agencia agencia = JPADAOFactory.getFactory().getAgenciaDAO().read(idAgencia);
		List<Factura> facturas = null;

		facturas = JPADAOFactory.getFactory().getFacturaDAO().getFacturasPendientesByClienteAndAgencia(cliente, agencia);
		
		return facturas;

	}

	public static int addProducto(int idBodegaDetalle, int idUsuario, int cantidad, int idFactura) {

		Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
		BodegaDetalle bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO().read(idBodegaDetalle);
		Producto producto = bodegaDetalle.getProducto();
		Empresa empresa = bodegaDetalle.getBodega().getAgencia().getEmpresa();
		int idDescuento = 1;//JPADAOFactory.getFactory().getDescuentoProductoDAO().getDescuentoId(producto, false);
		double valorDescuento = 1;// JPADAOFactory.getFactory().getDescuentoDAO().getValorDescuentoByFecha(idDescuento, false);
		double subtotal = (cantidad * bodegaDetalle.getPrecio()) - valorDescuento;

		try {
			Factura factura;
			if (idFactura != -1) {
				factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);
			} else {
				factura = new Factura();
			}

			factura.setAgencia(bodegaDetalle.getBodega().getAgencia());
			factura.setCliente(cliente);
			factura.setDescuento(factura.getDescuento() + valorDescuento);
			//TODO: poner en una enumeracion el valor Eliminado
			factura.setEliminado(false);
			factura.setEmpleadoAgencia(null);
			factura.setFecha(new Date());
			factura.setIva(empresa.getIva());
			factura.setMedioDePago(null);
			factura.setPagado(false);
			factura.setPendiente(true);
			factura.setSubtotal(subtotal);
			factura.setTotal(factura.getSubtotal() + subtotal);
			JPADAOFactory.getFactory().getFacturaDAO().create(factura);

			//Compruebo que la cantidad pedida no exceda del stock
			if (cantidad > bodegaDetalle.getCantidad()) {
				throw new Exception("La cantidad solicitada excede del stock actual");
			}

			//Creación de la Factura Detalle
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

			return factura.getId();
			/*
			 * //Disminuyo Stock.
			 * bodegaDetalle.setCantidad(bodegaDetalle.getCantidad() -
			 * cantidad);
			 * JPADAOFactory.getFactory().getBodegaDetalleDAO().update
			 * (bodegaDetalle);
			 */
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return -1;
		}

	}

	public static void deleteProductoXML(int idBodegaDetalle, int idFactura) {
		try {
			Factura factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);
			Set<FacturaDetalle> facturaDetalles = factura.getFacturaDetalle();
			for (FacturaDetalle facturaDetalle : facturaDetalles) {
				if (facturaDetalle.getBodegaDetalle().getId() == idBodegaDetalle) {
					//Agregar cantidad al Stock
					BodegaDetalle bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO().read(idBodegaDetalle);
					bodegaDetalle.setCantidad(bodegaDetalle.getCantidad() + facturaDetalle.getCantidad());
					//Eliminar detalle de FacturaDetalle
					facturaDetalle.setEliminado(true);
					JPADAOFactory.getFactory().getFacturaDetalleDAO().update(facturaDetalle);
					//JPADAOFactory.getFactory().getFacturaDetalleDAO().delete(facturaDetalle);
					break;
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void confirmarPedido(int idFactura) {
		try {
			Factura factura = JPADAOFactory.getFactory().getFacturaDAO().read(idFactura);
			factura.setPendiente(true);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Usuario usuario = null;
		Agencia agencia = JPADAOFactory.getFactory().getAgenciaDAO().read(1);
		int idFactura = -1;
		int n = 0;

		while (n != 9) {
			System.out.println("1.- Registrarse ");
			System.out.println("2.- Login ");
			System.out.println("3.- Comprar Producto ");
			System.out.println("4.- Enviar a Caja ");
			System.out.println("5.- Listar Carros Compras ");
			System.out.println("6.- Eliminar Producto");
			System.out.println("7.- Carro de Compras Actual");
			System.out.println("8.- Seleccionar Carro");
			System.out.println("9.- Salir ");
			System.out.println("Teclea la opción ");

			n = in.nextInt();

			switch (n) {
			case 2:
				System.out.println("username:");
				String username = in.next();
				System.out.println("pass:");
				String clave = in.next();
				usuario = login(username, clave);
				if (usuario != null)
					System.out.println("USUARIO LOGEADO: " + usuario.toString());
				else
					System.out.println("USUARIO NOLOGEADO");

				break;
			case 3:
				System.out.println("Ingresa id Producto (BodegaDetalle)");
				int idBodegaDetalle = in.nextInt();
				int idUsuario = usuario.getId();
				System.out.println("Ingresa Cantidad");
				int cantidad = in.nextInt();
				idFactura = addProducto(idBodegaDetalle, idUsuario, cantidad, idFactura);
				break;
			case 4:
				confirmarPedido(idFactura);
				break;
			case 5:
				idUsuario = usuario.getId();
				List<Factura> carros = listarCarrosCompra(idUsuario, agencia.getId());
				for (Factura factura : carros) {
					System.out.println("Los Carros de Compra son :" + factura.toString());
				}

				break;
			case 6:
				System.out.println("Ingresa id del Producto");
				idBodegaDetalle = in.nextInt();
				deleteProductoXML(idBodegaDetalle, idFactura);
				break;
			case 7:
				Set<FacturaDetalle>productos = listarProductosCarro(idFactura);
				int contador = 0;
				if (productos != null) {
					System.out.println("ID \tPRODUCTO\t CANTIDAD");
					for (FacturaDetalle facturaDetalle : productos) {
						if (!facturaDetalle.getEliminado()) {
							contador++;
							System.out.println(facturaDetalle.getId() + "\t" + facturaDetalle.getBodegaDetalle().getProducto().getNombre() + "\t"
									+ facturaDetalle.getCantidad());
						}
						if (contador == 0) {
							System.out.println("No existen Productos");
						}
					}
				} else {
					System.out.println("No existen productos");
				}
				break;
			case 8:
				System.out.println("Selecciona Carro");
				idFactura = in.nextInt();
				System.out.println("Carro Seleccionado");
				listarProductosCarro(idFactura);
				break;

			default:
				break;

			}
		}
		System.out.println("FIN DE EJECUCION. ");

		try {

			//addProductoXML(2, 3, 1);
			/*
			 * productos = listarProductosCuentaXML(3); if (productos != null) {
			 * for (FacturaDetalle facturaDetalle : productos) {
			 * System.out.println(facturaDetalle.getId()); } } else {
			 * System.out.println("No existen productos"); }
			 */
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

}
