package pfm.test;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import pfm.entidades.BodegaDetalle;
import pfm.entidades.Empresa;
import pfm.entidades.Factura;
import pfm.entidades.FacturaDetalle;
import pfm.entidades.Producto;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

public class TestRest {

	public static Set<FacturaDetalle> listarProductosCuentaXML(int idUsuario) {
		Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
		List<Factura> facturas = JPADAOFactory.getFactory().getFacturaDAO().getFacturasPendientesByCliente(cliente);

		if (facturas.size() == 1) {
			System.out.println("Existe un carro de compras on id: " + facturas.get(0).getId());
			return facturas.get(0).getFacturaDetalle();
		} else {
			return null;
		}
	}

	public static void addProductoXML(int idBodegaDetalle, int idUsuario, int cantidad) {
		try {
			Factura factura;
			Usuario cliente = JPADAOFactory.getFactory().getUsuarioDAO().read(idUsuario);
			List<Factura> facturas = JPADAOFactory.getFactory().getFacturaDAO().getFacturasPendientesByCliente(cliente);
			BodegaDetalle bodegaDetalle = JPADAOFactory.getFactory().getBodegaDetalleDAO().read(idBodegaDetalle);
			Producto producto = bodegaDetalle.getProducto();
			Empresa empresa = bodegaDetalle.getBodega().getAgencia().getEmpresa();
			int idDescuento = JPADAOFactory.getFactory().getDescuentoProductoDAO().getDescuentoId(producto, false);
			double valorDescuento = JPADAOFactory.getFactory().getDescuentoDAO().getValorDescuentoByFecha(idDescuento, false);
			double subtotal = (cantidad * bodegaDetalle.getPrecio()) - valorDescuento;

			//Compruebo que la cantidad pedida no exceda del stock
			if (cantidad > bodegaDetalle.getCantidad()) {
				throw new Exception("La cantidad solicitada excede del stock actual");
			}

			//Si no existe una factura que emula como carro de compras, creamos una.
			if (facturas.size() == 0) {
				factura = new Factura();
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
			} else {
				factura = facturas.get(0);
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

			//Disminuyo Stock.
			bodegaDetalle.setCantidad(bodegaDetalle.getCantidad() - cantidad);
			JPADAOFactory.getFactory().getBodegaDetalleDAO().update(bodegaDetalle);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
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
		int n = 0;

		while (n != 7) {
			System.out.println("1.- Registrarse ");
			System.out.println("2.- Login ");
			System.out.println("3.- Comprar Producto ");
			System.out.println("4.- Enviar a Caja ");
			System.out.println("5.- Listar Carro Compras ");
			System.out.println("6.- Eliminar Producto");
			System.out.println("7.- Salir ");
			System.out.println("Teclea la opción ");

			n = in.nextInt();

			switch (n) {
			case 3:
				System.out.println("Ingresa id Producto (BodegaDetalle)");
				int idBodegaDetalle = in.nextInt();
				System.out.println("Ingresa id Usuario");
				int idUsuario = in.nextInt();
				System.out.println("Ingresa Cantidad");
				int cantidad = in.nextInt();
				addProductoXML(idBodegaDetalle, idUsuario, cantidad);
				break;
			case 4:
				System.out.println("Ingresa id Factura");
				int idFactura = in.nextInt();
				confirmarPedido(idFactura);
				break;
			case 5:
				System.out.println("Ingresa id de Usuario");
				idUsuario = in.nextInt();
				Set<FacturaDetalle> productos = listarProductosCuentaXML(idUsuario);
				int contador = 0;
				if (productos != null) {
					for (FacturaDetalle facturaDetalle : productos) {
						if (!facturaDetalle.getEliminado()) {
							contador++;
							System.out.println(facturaDetalle.getId() + "\t" + facturaDetalle.getBodegaDetalle().getProducto().getNombre() + "\t"
									+ facturaDetalle.getCantidad());
						}
						if(contador==0){
							System.out.println("No existen Productos");
						}

					}
				} else {
					System.out.println("No existen productos");
				}
				break;
			case 6:
				System.out.println("Ingresa id del Producto");
				idBodegaDetalle = in.nextInt();
				System.out.println("Ingresa id de Factura");
				idFactura = in.nextInt();
				deleteProductoXML(idBodegaDetalle, idFactura);
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
