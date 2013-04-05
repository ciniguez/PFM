package pfm.beans.producto;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.ProductoDAO;
import pfm.entidades.Producto;

@ManagedBean(name = "listarProducto")
public class ListarProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	private List<Producto> lista;
	private List<Producto> filtered;

	public ListarProducto() {

	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	public List<Producto> getLista() {
		try{
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(productoDAO.find(attributes, values, order, index, size));
		return lista;
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return lista;
		}
	}

	public void setLista(List<Producto> lista) {
		this.lista = lista;
	}

	public List<Producto> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Producto> filtered) {
		this.filtered = filtered;
	}

	public void onEdit(RowEditEvent event) {
		try {
			Producto producto = new Producto();
			producto = (Producto) event.getObject();
			productoDAO.update(producto);

			FacesMessage msg = new FacesMessage("Producto actualizada",
					String.valueOf(((Producto) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Producto no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Producto cancelada",
				String.valueOf(((Producto) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}