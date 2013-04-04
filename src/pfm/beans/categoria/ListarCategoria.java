package pfm.beans.categoria;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.CategoriaDAO;
import pfm.entidades.Categoria;

@ManagedBean(name = "listarCategoria")
public class ListarCategoria implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	private List<Categoria> lista;
	private List<Categoria> filtered;

	public ListarCategoria() {

	}

	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDAO CategoriaDAO) {
		this.categoriaDAO = CategoriaDAO;
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

	public void onEdit(RowEditEvent event) {
		try {
			Categoria Categoria = new Categoria();
			Categoria = (Categoria) event.getObject();
			categoriaDAO.update(Categoria);

			FacesMessage msg = new FacesMessage("Categoria actualizada",
					String.valueOf(((Categoria) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Categoria no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Categoria cancelada",
				String.valueOf(((Categoria) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
