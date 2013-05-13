package pfm.beans.bodegaDetalle;

import java.io.Serializable;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.BodegaDetalleDAO;
import pfm.entidades.BodegaDetalle;

@ManagedBean(name = "listarBodegaDetalle")
@RequestScoped
public class ListarBodegaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDetalleDAO}")
	private BodegaDetalleDAO bodegaDetalleDAO;
	@ManagedProperty(value = "#{modificarBodegaDetalle}")
	private ModificarBodegaDetalle modificarBodegaDetalleBEAN;
	@ManagedProperty(value = "#{bajaBodegaDetalle}")
	private BajaBodegaDetalle bajaBodegaDetalleBEAN;
	@ManagedProperty(value = "#{altaBodegaDetalle}")
	private AltaBodegaDetalle altaBodegaDetalleBEAN;
	private List<BodegaDetalle> lista;
	private List<BodegaDetalle> filtered;
	private BodegaDetalle[] selectedBodegaDetalle;

	public ListarBodegaDetalle() {
	}

	public BodegaDetalleDAO getBodegaDetalleDAO() {
		return bodegaDetalleDAO;
	}

	public void setBodegaDetalleDAO(BodegaDetalleDAO bodegaDetalleDAO) {
		this.bodegaDetalleDAO = bodegaDetalleDAO;
	}

	public ModificarBodegaDetalle getModificarBodegaDetalleBEAN() {
		return modificarBodegaDetalleBEAN;
	}

	public void setModificarBodegaDetalleBEAN(
			ModificarBodegaDetalle modificarBodegaDetalleBEAN) {
		this.modificarBodegaDetalleBEAN = modificarBodegaDetalleBEAN;
	}

	public BajaBodegaDetalle getBajaBodegaDetalleBEAN() {
		return bajaBodegaDetalleBEAN;
	}

	public void setBajaBodegaDetalleBEAN(BajaBodegaDetalle bajaBodegaDetalleBEAN) {
		this.bajaBodegaDetalleBEAN = bajaBodegaDetalleBEAN;
	}

	public AltaBodegaDetalle getAltaBodegaDetalleBEAN() {
		return altaBodegaDetalleBEAN;
	}

	public void setAltaBodegaDetalleBEAN(AltaBodegaDetalle altaBodegaDetalleBEAN) {
		this.altaBodegaDetalleBEAN = altaBodegaDetalleBEAN;
	}

	public List<BodegaDetalle> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(bodegaDetalleDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<BodegaDetalle> lista) {
		this.lista = lista;
	}

	public List<BodegaDetalle> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<BodegaDetalle> filtered) {
		this.filtered = filtered;
	}

	public BodegaDetalle[] getSelectedBodegaDetalle() {
		return selectedBodegaDetalle;
	}

	public void setSelectedBodegaDetalle(BodegaDetalle[] selectedBodegaDetalle) {
		this.selectedBodegaDetalle = selectedBodegaDetalle;
	}

	public String onCrear() {
		return "crearBodegaDetalle";
	}

	public void onModificar(RowEditEvent event) {

		modificarBodegaDetalleBEAN.setBodegaDetalle((BodegaDetalle) event
				.getObject());
		modificarBodegaDetalleBEAN.modificar();

	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Bodega cancelada",
				String.valueOf(((BodegaDetalle) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedBodegaDetalle.length > 0) {
			for (BodegaDetalle b : selectedBodegaDetalle) {
				bajaBodegaDetalleBEAN.setBodegaDetalle(b);
				bajaBodegaDetalleBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas productos por bodega");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodegaDetalle";
	}

	public String onAlta() {
		if (selectedBodegaDetalle.length > 0) {
			for (BodegaDetalle b : selectedBodegaDetalle) {
				altaBodegaDetalleBEAN.setBodegaDetalle(b);
				altaBodegaDetalleBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas productos por bodega");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodegaDetalle";
	}
}
