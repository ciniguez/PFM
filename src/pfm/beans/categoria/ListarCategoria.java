package pfm.beans.categoria;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.CategoriaDAO;
import pfm.entidades.Categoria;

@ManagedBean(name = "listarCategoria")
@SessionScoped
public class ListarCategoria implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	@ManagedProperty(value = "#{modificarCategoria}")
	private ModificarCategoria modificarCategoriaBEAN;
	@ManagedProperty(value = "#{bajaCategoria}")
	private BajaCategoria bajaCategoriaBEAN;
	@ManagedProperty(value = "#{altaCategoria}")
	private AltaCategoria altaCategoriaBEAN;
	private List<Categoria> lista;
	private List<Categoria> filtered;
	private Categoria[] selectedCategorias;

	public ListarCategoria() {

	}

	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

	public ModificarCategoria getModificarCategoriaBEAN() {
		return modificarCategoriaBEAN;
	}

	public void setModificarCategoriaBEAN(ModificarCategoria modificarCategoriaBEAN) {
		this.modificarCategoriaBEAN = modificarCategoriaBEAN;
	}

	public BajaCategoria getBajaCategoriaBEAN() {
		return bajaCategoriaBEAN;
	}

	public void setBajaCategoriaBEAN(BajaCategoria bajaCategoriaBEAN) {
		this.bajaCategoriaBEAN = bajaCategoriaBEAN;
	}

	public AltaCategoria getAltaCategoriaBEAN() {
		return altaCategoriaBEAN;
	}

	public void setAltaCategoriaBEAN(AltaCategoria altaCategoriaBEAN) {
		this.altaCategoriaBEAN = altaCategoriaBEAN;
	}

	public List<Categoria> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(categoriaDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Categoria> lista) {
		this.lista = lista;
	}

	public List<Categoria> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Categoria> filtered) {
		this.filtered = filtered;
	}

	public Categoria[] getSelectedCategorias() {
		return selectedCategorias;
	}

	public void setSelectedCategorias(Categoria[] selectedCategorias) {
		this.selectedCategorias = selectedCategorias;
	}

	public String onCrear() {
		return "crearCategoria";
	}

	public void onModificar(RowEditEvent event) {
		modificarCategoriaBEAN.setCategoria((Categoria) event.getObject());
		modificarCategoriaBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Categoria cancelada",
				String.valueOf(((Categoria) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedCategorias.length > 0) {
			for (Categoria e : selectedCategorias) {
				bajaCategoriaBEAN.setCategoria(e);
				bajaCategoriaBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas categorias");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarCategoria";
	}

	public String onAlta() {
		if (selectedCategorias.length > 0) {
			for (Categoria e : selectedCategorias) {
				altaCategoriaBEAN.setCategoria(e);
				altaCategoriaBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas categorias");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarCategoria";
	}
}