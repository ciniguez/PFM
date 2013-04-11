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
	@ManagedProperty(value = "#{modificarMarca}")
	private ModificarMarca modificarMarcaBEAN;
	@ManagedProperty(value = "#{bajaMarca}")
	private BajaMarca bajaMarcaBEAN;
	@ManagedProperty(value = "#{altaMarca}")
	private AltaMarca altaMarcaBEAN;
	private List<Marca> lista;
	private List<Marca> filtered;
	private Marca[] selectedMarcas;

	public ListarMarca() {

	}

	public MarcaDAO getMarcaDAO() {
		return marcaDAO;
	}

	public void setMarcaDAO(MarcaDAO marcaDAO) {
		this.marcaDAO = marcaDAO;
	}

	public ModificarMarca getModificarMarcaBEAN() {
		return modificarMarcaBEAN;
	}

	public void setModificarMarcaBEAN(ModificarMarca modificarMarcaBEAN) {
		this.modificarMarcaBEAN = modificarMarcaBEAN;
	}

	public BajaMarca getBajaMarcaBEAN() {
		return bajaMarcaBEAN;
	}

	public void setBajaMarcaBEAN(BajaMarca bajaMarcaBEAN) {
		this.bajaMarcaBEAN = bajaMarcaBEAN;
	}

	public AltaMarca getAltaMarcaBEAN() {
		return altaMarcaBEAN;
	}

	public void setAltaMarcaBEAN(AltaMarca altaMarcaBEAN) {
		this.altaMarcaBEAN = altaMarcaBEAN;
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

	public Marca[] getSelectedMarcas() {
		return selectedMarcas;
	}

	public void setSelectedMarcas(Marca[] selectedMarcas) {
		this.selectedMarcas = selectedMarcas;
	}

	public String onCrear() {
		return "crearMarca";
	}

	public void onModificar(RowEditEvent event) {
		modificarMarcaBEAN.setMarca((Marca) event.getObject());
		modificarMarcaBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Marca cancelada",
				String.valueOf(((Marca) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedMarcas.length > 0) {
			for (Marca e : selectedMarcas) {
				bajaMarcaBEAN.setMarca(e);
				bajaMarcaBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas marcas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarMarca";
	}

	public String onAlta() {
		if (selectedMarcas.length > 0) {
			for (Marca e : selectedMarcas) {
				altaMarcaBEAN.setMarca(e);
				altaMarcaBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas marcas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarMarca";
	}
}