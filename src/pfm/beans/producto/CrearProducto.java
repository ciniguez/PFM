package pfm.beans.producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pfm.dao.CategoriaDAO;
import pfm.dao.MarcaDAO;
import pfm.dao.ProductoDAO;
import pfm.entidades.Categoria;
import pfm.entidades.Marca;
import pfm.entidades.Producto;

@ManagedBean(name = "crearProducto")
public class CrearProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	@ManagedProperty(value = "#{DAOFactory.marcaDAO}")
	private MarcaDAO marcaDAO;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	private Producto producto = new Producto();
	private SelectItem[] categorias;
	private SelectItem[] marcas;
	private String categoria;
	private String marca;

	public CrearProducto() {

	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	public MarcaDAO getMarcaDAO() {
		return marcaDAO;
	}

	public void setMarcaDAO(MarcaDAO marcaDAO) {
		this.marcaDAO = marcaDAO;
	}

	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public SelectItem[] getCategorias() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Categoria> listaCategorias = new ArrayList<Categoria>();
		listaCategorias = categoriaDAO.find(attributes, values, order, index,
				size);
		this.categorias = new SelectItem[listaCategorias.size()];
		for (Categoria e : listaCategorias) {
			this.categorias[i] = new SelectItem(e.getId(), e.getNombre());
			i++;
		}
		return categorias;
	}

	public void setCategorias(SelectItem[] categorias) {
		this.categorias = categorias;
	}

	public SelectItem[] getMarcas() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Marca> listaMarcas = new ArrayList<Marca>();
		listaMarcas = marcaDAO.find(attributes, values, order, index, size);
		this.marcas = new SelectItem[listaMarcas.size()];
		for (Marca e : listaMarcas) {
			this.marcas[i] = new SelectItem(e.getId(), e.getNombre());
			i++;
		}
		return marcas;
	}

	public void setMarcas(SelectItem[] marcas) {
		this.marcas = marcas;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String crear() {
		try {
			producto.setCategoria(categoriaDAO.read(Integer
					.parseInt(getCategoria())));
			producto.setMarca(marcaDAO.read(Integer.parseInt(getMarca())));
			productoDAO.create(producto);
			FacesMessage msg = new FacesMessage("Producto creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Producto no creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarProducto";
	}
}
