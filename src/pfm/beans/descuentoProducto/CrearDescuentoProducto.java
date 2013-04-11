package pfm.beans.descuentoProducto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pfm.dao.DescuentoDAO;
import pfm.dao.DescuentoProductoDAO;
import pfm.dao.ProductoDAO;
import pfm.entidades.Descuento;
import pfm.entidades.DescuentoProducto;
import pfm.entidades.Producto;

@ManagedBean(name = "crearDescuentoProducto")
public class CrearDescuentoProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoProductoDAO}")
	private DescuentoProductoDAO descuentoProductoDAO;
	@ManagedProperty(value = "#{DAOFactory.descuentoDAO}")
	private DescuentoDAO descuentoDAO;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	private DescuentoProducto descuentoProducto = new DescuentoProducto();
	private SelectItem[] descuentos;
	private SelectItem[] productos;
	private String descuento;
	private String producto;

	public CrearDescuentoProducto() {

	}

	public DescuentoProductoDAO getDescuentoProductoDAO() {
		return descuentoProductoDAO;
	}

	public void setDescuentoProductoDAO(
			DescuentoProductoDAO descuentoProductoDAO) {
		this.descuentoProductoDAO = descuentoProductoDAO;
	}

	public DescuentoDAO getDescuentoDAO() {
		return descuentoDAO;
	}

	public void setDescuentoDAO(DescuentoDAO descuentoDAO) {
		this.descuentoDAO = descuentoDAO;
	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	public DescuentoProducto getDescuentoProducto() {
		return descuentoProducto;
	}

	public void setDescuentoProducto(DescuentoProducto descuentoProducto) {
		this.descuentoProducto = descuentoProducto;
	}

	public SelectItem[] getDescuentos() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Descuento> listaDescuentos = new ArrayList<Descuento>();
		listaDescuentos = descuentoDAO.find(attributes, values, order, index,
				size);
		this.descuentos = new SelectItem[listaDescuentos.size()];
		for (Descuento d : listaDescuentos) {
			this.descuentos[i] = new SelectItem(d.getId(), d.getNombre());
			i++;
		}

		return descuentos;
	}

	public void setDescuentos(SelectItem[] descuentos) {
		this.descuentos = descuentos;
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

	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String crear() {
		try {
			descuentoProducto.setDescuento(descuentoDAO.read(Integer
					.parseInt(getDescuento())));
			descuentoProducto.setProducto(productoDAO.read(Integer
					.parseInt(getProducto())));
			descuentoProductoDAO.create(descuentoProducto);

			FacesMessage msg = new FacesMessage("Descuento por Producto creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {

			FacesMessage msg = new FacesMessage("Error",
					"Descuento por Producto no creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarDescuentoProducto";
	}
}
