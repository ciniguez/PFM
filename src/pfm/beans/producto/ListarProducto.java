package pfm.beans.producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import pfm.dao.CategoriaDAO;
import pfm.dao.MarcaDAO;
import pfm.dao.ProductoDAO;
import pfm.entidades.Categoria;
import pfm.entidades.Marca;
import pfm.entidades.Producto;

@ManagedBean(name = "listarProducto")
@SessionScoped
public class ListarProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	@ManagedProperty(value = "#{DAOFactory.marcaDAO}")
	private MarcaDAO marcaDAO;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	@ManagedProperty(value = "#{modificarProducto}")
	private ModificarProducto modificarProductoBEAN;
	@ManagedProperty(value = "#{bajaProducto}")
	private BajaProducto bajaProductoBEAN;
	@ManagedProperty(value = "#{altaProducto}")
	private AltaProducto altaProductoBEAN;
	private List<Producto> lista;
	private List<Producto> filtered;
	private SelectItem[] marcas;
	private String marca;
	private SelectItem[] categorias;
	private String categoria;
	private Producto[] selectedProductos;

	public ListarProducto() {

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

	public ModificarProducto getModificarProductoBEAN() {
		return modificarProductoBEAN;
	}

	public void setModificarProductoBEAN(ModificarProducto modificarProductoBEAN) {
		this.modificarProductoBEAN = modificarProductoBEAN;
	}

	public BajaProducto getBajaProductoBEAN() {
		return bajaProductoBEAN;
	}

	public void setBajaProductoBEAN(BajaProducto bajaProductoBEAN) {
		this.bajaProductoBEAN = bajaProductoBEAN;
	}

	public AltaProducto getAltaProductoBEAN() {
		return altaProductoBEAN;
	}

	public void setAltaProductoBEAN(AltaProducto altaProductoBEAN) {
		this.altaProductoBEAN = altaProductoBEAN;
	}

	public List<Producto> getLista() {
		try {
			String[] attributes = {};
			String[] values = {};
			String order = "id";
			int index = -1;
			int size = -1;
			setLista(productoDAO.find(attributes, values, order, index, size));
			return lista;
		} catch (Exception ex) {
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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Producto[] getSelectedProductos() {
		return selectedProductos;
	}

	public void setSelectedProductos(Producto[] selectedProductos) {
		this.selectedProductos = selectedProductos;
	}

	public String onCrear() {
		return "crearProducto";
	}

	public void onModificar(RowEditEvent event) {

		Producto producto = new Producto();
		producto = (Producto) event.getObject();
		producto.setCategoria(categoriaDAO.read(Integer
				.parseInt(getCategoria())));
		producto.setMarca(marcaDAO.read(Integer.parseInt(getMarca())));
		modificarProductoBEAN.setProducto(producto);
		modificarProductoBEAN.modificar();

	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Producto cancelado",
				String.valueOf(((Producto) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedProductos.length > 0) {
			for (Producto p : selectedProductos) {
				bajaProductoBEAN.setProducto(p);
				bajaProductoBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas productos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarProducto";
	}

	public String onAlta() {
		if (selectedProductos.length > 0) {
			for (Producto p : selectedProductos) {
				altaProductoBEAN.setProducto(p);
				altaProductoBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas productos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarProducto";
	}
}