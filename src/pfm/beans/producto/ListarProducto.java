package pfm.beans.producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class ListarProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	@ManagedProperty(value = "#{DAOFactory.marcaDAO}")
	private MarcaDAO marcaDAO;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	private List<Producto> lista;
	private List<Producto> filtered;
	private SelectItem[] marcas;
	private String marca;
	private SelectItem[] categorias;
	private String categoria;

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
			System.out.println(this.getCategoria());
			
			Producto producto = new Producto();
			producto = (Producto) event.getObject();
			producto.setCategoria(categoriaDAO.read(Integer.parseInt(getCategoria())));
			producto.setMarca(marcaDAO.read(Integer.parseInt(getMarca())));
			System.out.println(producto.toString());
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
		listaCategorias = categoriaDAO.find(attributes, values, order, index, size);
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
}