
package pfm.beans.mediopago;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.MedioPagoDAO;
import pfm.entidades.MedioDePago;

@ManagedBean(name = "listarMedioPago")
public class ListarMedioPago implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.medioPagoDAO}")
	private MedioPagoDAO medioPagoDAO;
	private List<MedioDePago> lista;
	private List<MedioDePago> filtered;

	public ListarMedioPago() {

	}

	public MedioPagoDAO getMedioPagoDAO() {
		return medioPagoDAO;
	}

	public void setMedioPagoDAO(MedioPagoDAO medioPagoDAO) {
		this.medioPagoDAO = medioPagoDAO;
	}

	public List<MedioDePago> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(medioPagoDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<MedioDePago> lista) {
		this.lista = lista;
	}

	public List<MedioDePago> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<MedioDePago> filtered) {
		this.filtered = filtered;
	}

	public void onEdit(RowEditEvent event) {
		try {
			MedioDePago medioPago = new MedioDePago();
			medioPago = (MedioDePago) event.getObject();
			medioPagoDAO.update(medioPago);

			FacesMessage msg = new FacesMessage("MedioPago actualizada",
					String.valueOf(((MedioDePago) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"MedioPago no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("MedioPago cancelada",
				String.valueOf(((MedioDePago) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}