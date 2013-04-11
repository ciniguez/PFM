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
	@ManagedProperty(value = "#{modificarRol}")
	private ModificarRol modificarRolBEAN;
	@ManagedProperty(value = "#{bajaRol}")
	private BajaRol bajaRolBEAN;
	@ManagedProperty(value = "#{altaRol}")
	private AltaRol altaRolBEAN;
	private List<Rol> lista;
	private List<Rol> filtered;
	private Rol[] selectedRols;

	public ListarRol() {

	}

	public RolDAO getRolDAO() {
		return rolDAO;
	}

	public void setRolDAO(RolDAO rolDAO) {
		this.rolDAO = rolDAO;
	}

	public ModificarRol getModificarRolBEAN() {
		return modificarRolBEAN;
	}

	public void setModificarRolBEAN(ModificarRol modificarRolBEAN) {
		this.modificarRolBEAN = modificarRolBEAN;
	}

	public BajaRol getBajaRolBEAN() {
		return bajaRolBEAN;
	}

	public void setBajaRolBEAN(BajaRol bajaRolBEAN) {
		this.bajaRolBEAN = bajaRolBEAN;
	}

	public AltaRol getAltaRolBEAN() {
		return altaRolBEAN;
	}

	public void setAltaRolBEAN(AltaRol altaRolBEAN) {
		this.altaRolBEAN = altaRolBEAN;
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

	public Rol[] getSelectedRols() {
		return selectedRols;
	}

	public void setSelectedRols(Rol[] selectedRols) {
		this.selectedRols = selectedRols;
	}

	public String onCrear() {
		return "crearRol";
	}

	public void onModificar(RowEditEvent event) {
		modificarRolBEAN.setRol((Rol) event.getObject());
		modificarRolBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Rol cancelada",
				String.valueOf(((Rol) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedRols.length > 0) {
			for (Rol e : selectedRols) {
				bajaRolBEAN.setRol(e);
				bajaRolBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas rols");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarRol";
	}

	public String onAlta() {
		if (selectedRols.length > 0) {
			for (Rol e : selectedRols) {
				altaRolBEAN.setRol(e);
				altaRolBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas rols");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarRol";
	}
}