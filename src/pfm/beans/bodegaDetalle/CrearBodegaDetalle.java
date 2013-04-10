package pfm.beans.bodegaDetalle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pfm.dao.BodegaDAO;
import pfm.dao.BodegaDetalleDAO;
import pfm.dao.ProductoDAO;
import pfm.entidades.Bodega;
import pfm.entidades.BodegaDetalle;
import pfm.entidades.Producto;

@ManagedBean(name = "crearBodegaDetalle")
public class CrearBodegaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDetalleDAO}")
	private BodegaDetalleDAO bodegaDetalleDAO;
	@ManagedProperty(value = "#{DAOFactory.bodegaDAO}")
	private BodegaDAO bodegaDAO;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	private BodegaDetalle bodegaDetalle = new BodegaDetalle();
	private SelectItem[] bodegas;
	private SelectItem[] productos;
	private String bodega;
	private String producto;

	public CrearBodegaDetalle() {
	}

	public BodegaDetalleDAO getBodegaDetalleDAO() {
		return bodegaDetalleDAO;
	}

	public void setBodegaDetalleDAO(BodegaDetalleDAO bodegaDetalleDAO) {
		this.bodegaDetalleDAO = bodegaDetalleDAO;
	}

	public BodegaDAO getBodegaDAO() {
		return bodegaDAO;
	}

	public void setBodegaDAO(BodegaDAO bodegaDAO) {
		this.bodegaDAO = bodegaDAO;
	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	public BodegaDetalle getBodegaDetalle() {
		return bodegaDetalle;
	}

	public void setBodegaDetalle(BodegaDetalle bodegaDetalle) {
		this.bodegaDetalle = bodegaDetalle;
	}

	public SelectItem[] getBodegas() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Bodega> listaBodegas = new ArrayList<Bodega>();
		listaBodegas = bodegaDAO.find(attributes, values, order, index, size);
		this.bodegas = new SelectItem[listaBodegas.size()];
		for (Bodega b : listaBodegas) {
			this.bodegas[i] = new SelectItem(b.getId(), b.getNombre());
			i++;
		}
		return bodegas;
	}

	public void setBodegas(SelectItem[] bodegas) {
		this.bodegas = bodegas;
	}

	public SelectItem[] getProductos() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Producto> listaProductos = new ArrayList<Producto>();
		listaProductos = productoDAO.find(attributes, values, order, index,
				size);
		this.productos = new SelectItem[listaProductos.size()];
		for (Producto p : listaProductos) {
			this.productos[i] = new SelectItem(p.getId(), p.getNombre());
			i++;
		}
		return productos;
	}

	public void setProductos(SelectItem[] productos) {
		this.productos = productos;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String crear() {
		try {
			bodegaDetalle.setBodega(bodegaDAO.read(Integer
					.parseInt(getBodega())));
			bodegaDetalle.setProducto(productoDAO.read(Integer
					.parseInt(getProducto())));
			bodegaDetalleDAO.create(bodegaDetalle);

			FacesMessage msg = new FacesMessage("Producto por Bodega creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {

			FacesMessage msg = new FacesMessage("Error",
					"Producto por Bodega no creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarBodegaDetalle";
	}

}
