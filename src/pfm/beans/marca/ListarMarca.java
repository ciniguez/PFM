
package pfm.beans.marca;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.MarcaDAO;
import pfm.entidades.Marca;

@ManagedBean(name = "listarMarca")
public class ListarMarca implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.marcaDAO}")
	private MarcaDAO marcaDAO;
	private List<Marca> lista;
	private List<Marca> filtered;

	public ListarMarca() {

	}

	public MarcaDAO getMarcaDAO() {
		return marcaDAO;
	}

	public void setMarcaDAO(MarcaDAO marcaDAO) {
		this.marcaDAO = marcaDAO;
	}

	public List<Marca> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(marcaDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Marca> lista) {
		this.lista = lista;
	}

	public List<Marca> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Marca> filtered) {
		this.filtered = filtered;
	}

	public void onEdit(RowEditEvent event) {
		try {
			Marca marca = new Marca();
			marca = (Marca) event.getObject();
			marcaDAO.update(marca);

			FacesMessage msg = new FacesMessage("Marca actualizada",
					String.valueOf(((Marca) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Marca no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Marca cancelada",
				String.valueOf(((Marca) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}