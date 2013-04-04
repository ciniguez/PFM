package pfm.beans.rol;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.RolDAO;
import pfm.entidades.Rol;

@ManagedBean(name = "listarRol")
public class ListarRol implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.rolDAO}")
	private RolDAO rolDAO;
	private List<Rol> lista;
	private List<Rol> filtered;

	public ListarRol() {

	}

	public RolDAO getRolDAO() {
		return rolDAO;
	}

	public void setRolDAO(RolDAO rolDAO) {
		this.rolDAO = rolDAO;
	}

	public List<Rol> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(rolDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Rol> lista) {
		this.lista = lista;
	}

	public List<Rol> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Rol> filtered) {
		this.filtered = filtered;
	}

	public void onEdit(RowEditEvent event) {
		try {
			Rol rol = new Rol();
			rol = (Rol) event.getObject();
			rolDAO.update(rol);

			FacesMessage msg = new FacesMessage("Rol actualizada",
					String.valueOf(((Rol) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Rol no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Rol cancelada",
				String.valueOf(((Rol) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}