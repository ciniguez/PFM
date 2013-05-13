package pfm.beans.producto;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.ProductoDAO;
import pfm.entidades.Producto;

@ManagedBean(name = "bajaProducto")
@RequestScoped
public class BajaProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	private Producto producto;

	public BajaProducto() {

	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String baja() {
		try {
			producto.setEliminado(true);
			getProductoDAO().update(producto);
			FacesMessage msg = new FacesMessage("Producto dado de baja",
					String.valueOf(producto.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Producto no dado de baja");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}

		return "listarProducto";
	}
}
