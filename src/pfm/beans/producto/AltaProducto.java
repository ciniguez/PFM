
package pfm.beans.producto;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.ProductoDAO;
import pfm.entidades.Categoria;
import pfm.entidades.Marca;
import pfm.entidades.Producto;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "altaProducto")
public class AltaProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	
	private Producto producto = new Producto();
	private List<Categoria> listaCategoria;
	private List<Marca> listaMarca;
	private int idCategoriaSeleccionada;
	private int idMarcaSeleccionada;

	public AltaProducto() {
		String[] attributes = {};
		String[] values={};
		String order = "id";
		int index = 0;
		int size= 0;
		this.listaCategoria = JPADAOFactory.getFactory().getCategoriaDAO().find(attributes, values, order, index, size);
		this.listaMarca = JPADAOFactory.getFactory().getMarcaDAO().find(attributes, values, order, index, size);
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

	public String create() {
		try {
			producto.setCategoria(JPADAOFactory.getFactory().getCategoriaDAO().read(this.getCategoriaSeleccionada()));
			producto.setMarca(JPADAOFactory.getFactory().getMarcaDAO().read(this.getMarcaSeleccionada()));
			productoDAO.create(producto);
			FacesMessage msg = new FacesMessage("Producto creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Producto no creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarProducto";
	}

	public List<Categoria> getListaCategoria() {
		return this.listaCategoria;
	}

	public List<Marca> getListaMarca() {
		return this.listaMarca;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public void setListaMarca(List<Marca> listaMarca) {
		this.listaMarca = listaMarca;
	}



	public int getCategoriaSeleccionada() {
		return idCategoriaSeleccionada;
	}

	public void setCategoriaSeleccionada(int categoriaSeleccionada) {
		this.idCategoriaSeleccionada = categoriaSeleccionada;
	}

	public int getMarcaSeleccionada() {
		return idMarcaSeleccionada;
	}

	public void setMarcaSeleccionada(int marcaSeleccionada) {
		this.idMarcaSeleccionada = marcaSeleccionada;
	}



}
