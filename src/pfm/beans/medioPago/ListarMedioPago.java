package pfm.beans.medioPago;

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
	@ManagedProperty(value = "#{modificarMedioPago}")
	private ModificarMedioPago modificarMedioPagoBEAN;
	@ManagedProperty(value = "#{bajaMedioPago}")
	private BajaMedioPago bajaMedioPagoBEAN;
	@ManagedProperty(value = "#{altaMedioPago}")
	private AltaMedioPago altaMedioPagoBEAN;
	private List<MedioDePago> lista;
	private List<MedioDePago> filtered;
	private MedioDePago[] selectedMedioPagos;

	public ListarMedioPago() {

	}

	public MedioPagoDAO getMedioPagoDAO() {
		return medioPagoDAO;
	}

	public void setMedioPagoDAO(MedioPagoDAO medioPagoDAO) {
		this.medioPagoDAO = medioPagoDAO;
	}

	public ModificarMedioPago getModificarMedioPagoBEAN() {
		return modificarMedioPagoBEAN;
	}

	public void setModificarMedioPagoBEAN(ModificarMedioPago modificarMedioPagoBEAN) {
		this.modificarMedioPagoBEAN = modificarMedioPagoBEAN;
	}

	public BajaMedioPago getBajaMedioPagoBEAN() {
		return bajaMedioPagoBEAN;
	}

	public void setBajaMedioPagoBEAN(BajaMedioPago bajaMedioPagoBEAN) {
		this.bajaMedioPagoBEAN = bajaMedioPagoBEAN;
	}

	public AltaMedioPago getAltaMedioPagoBEAN() {
		return altaMedioPagoBEAN;
	}

	public void setAltaMedioPagoBEAN(AltaMedioPago altaMedioPagoBEAN) {
		this.altaMedioPagoBEAN = altaMedioPagoBEAN;
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

	public MedioDePago[] getSelectedMedioPagos() {
		return selectedMedioPagos;
	}

	public void setSelectedMedioPagos(MedioDePago[] selectedMedioPagos) {
		this.selectedMedioPagos = selectedMedioPagos;
	}

	public String onCrear() {
		return "crearMedioPago";
	}

	public void onModificar(RowEditEvent event) {
		modificarMedioPagoBEAN.setMedioPago((MedioDePago) event.getObject());
		modificarMedioPagoBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("MedioPago cancelada", String.valueOf(((MedioDePago) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedMedioPagos.length > 0) {
			for (MedioDePago e : selectedMedioPagos) {
				bajaMedioPagoBEAN.setMedioPago(e);
				bajaMedioPagoBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error", "Debe seleccionar uno o mas medioPagos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarMedioPago";
	}

	public String onAlta() {
		if (selectedMedioPagos.length > 0) {
			for (MedioDePago e : selectedMedioPagos) {
				altaMedioPagoBEAN.setMedioPago(e);
				altaMedioPagoBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error", "Debe seleccionar uno o mas medioPagos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarMedioPago";
	}
}