package pfm.beans.descuento;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.DescuentoDAO;
import pfm.entidades.Descuento;

@ManagedBean(name = "listarDescuento")
public class ListarDescuento implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoDAO}")
	private DescuentoDAO descuentoDAO;
	private List<Descuento> lista;
	private List<Descuento> filtered;

	public ListarDescuento() {
	}

	public DescuentoDAO getDescuentoDAO() {
		return descuentoDAO;
	}

	public void setDescuentoDAO(DescuentoDAO descuentoDAO) {
		this.descuentoDAO = descuentoDAO;
	}

	public List<Descuento> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(descuentoDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Descuento> lista) {
		this.lista = lista;
	}

	public List<Descuento> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Descuento> filtered) {
		this.filtered = filtered;
	}

	public void onEdit(RowEditEvent event) {
		try {
			Descuento descuento = new Descuento();
			descuento = (Descuento) event.getObject();
			descuentoDAO.update(descuento);

			FacesMessage msg = new FacesMessage("Descuento actualizada",
					String.valueOf(((Descuento) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Descuento no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Descuento cancelada",
				String.valueOf(((Descuento) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
