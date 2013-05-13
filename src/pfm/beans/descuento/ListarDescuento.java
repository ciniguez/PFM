package pfm.beans.descuento;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.DescuentoDAO;
import pfm.entidades.Descuento;

@ManagedBean(name = "listarDescuento")
@RequestScoped
public class ListarDescuento implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoDAO}")
	private DescuentoDAO descuentoDAO;
	@ManagedProperty(value = "#{modificarDescuento}")
	private ModificarDescuento modificarDescuentoBEAN;
	@ManagedProperty(value = "#{bajaDescuento}")
	private BajaDescuento bajaDescuentoBEAN;
	@ManagedProperty(value = "#{altaDescuento}")
	private AltaDescuento altaDescuentoBEAN;
	private List<Descuento> lista;
	private List<Descuento> filtered;
	private Descuento[] selectedDescuentos;

	public ListarDescuento() {
	}

	public DescuentoDAO getDescuentoDAO() {
		return descuentoDAO;
	}

	public void setDescuentoDAO(DescuentoDAO descuentoDAO) {
		this.descuentoDAO = descuentoDAO;
	}

	public ModificarDescuento getModificarDescuentoBEAN() {
		return modificarDescuentoBEAN;
	}

	public void setModificarDescuentoBEAN(
			ModificarDescuento modificarDescuentoBEAN) {
		this.modificarDescuentoBEAN = modificarDescuentoBEAN;
	}

	public BajaDescuento getBajaDescuentoBEAN() {
		return bajaDescuentoBEAN;
	}

	public void setBajaDescuentoBEAN(BajaDescuento bajaDescuentoBEAN) {
		this.bajaDescuentoBEAN = bajaDescuentoBEAN;
	}

	public AltaDescuento getAltaDescuentoBEAN() {
		return altaDescuentoBEAN;
	}

	public void setAltaDescuentoBEAN(AltaDescuento altaDescuentoBEAN) {
		this.altaDescuentoBEAN = altaDescuentoBEAN;
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

	public Descuento[] getSelectedDescuentos() {
		return selectedDescuentos;
	}

	public void setSelectedDescuentos(Descuento[] selectedDescuentos) {
		this.selectedDescuentos = selectedDescuentos;
	}

	public String onCrear() {
		return "crearDescuento";
	}

	public void onModificar(RowEditEvent event) {
		modificarDescuentoBEAN.setDescuento((Descuento) event.getObject());
		modificarDescuentoBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Descuento cancelada",
				String.valueOf(((Descuento) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedDescuentos.length > 0) {
			for (Descuento d : selectedDescuentos) {
				bajaDescuentoBEAN.setDescuento(d);
				bajaDescuentoBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas descuentos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarDescuento";
	}

	public String onAlta() {
		if (selectedDescuentos.length > 0) {
			for (Descuento d : selectedDescuentos) {
				altaDescuentoBEAN.setDescuento(d);
				altaDescuentoBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas descuentos");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarDescuento";
	}

}
