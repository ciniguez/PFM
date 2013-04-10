package pfm.beans.bodega;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import pfm.dao.AgenciaDAO;
import pfm.dao.BodegaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Bodega;

@ManagedBean(name = "listarBodega")
public class ListarBodega implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDAO}")
	private BodegaDAO bodegaDAO;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	@ManagedProperty(value = "#{modificarBodega}")
	private ModificarBodega modificarBodegaBEAN;
	@ManagedProperty(value = "#{altaBodega}")
	private AltaBodega altaBodegaBEAN;
	@ManagedProperty(value = "#{bajaBodega}")
	private BajaBodega bajaBodegaBEAN;
	private List<Bodega> lista;
	private List<Bodega> filtered;
	private SelectItem[] agencias;
	private String agencia;
	private Bodega[] selectedBodegas;

	public ListarBodega() {
	}

	public BodegaDAO getBodegaDAO() {
		return bodegaDAO;
	}

	public void setBodegaDAO(BodegaDAO bodegaDAO) {
		this.bodegaDAO = bodegaDAO;
	}

	public AgenciaDAO getAgenciaDAO() {
		return agenciaDAO;
	}

	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}

	public ModificarBodega getModificarBodegaBEAN() {
		return modificarBodegaBEAN;
	}

	public void setModificarBodegaBEAN(ModificarBodega modificarBodegaBEAN) {
		this.modificarBodegaBEAN = modificarBodegaBEAN;
	}

	public AltaBodega getAltaBodegaBEAN() {
		return altaBodegaBEAN;
	}

	public void setAltaBodegaBEAN(AltaBodega altaBodegaBEAN) {
		this.altaBodegaBEAN = altaBodegaBEAN;
	}

	public BajaBodega getBajaBodegaBEAN() {
		return bajaBodegaBEAN;
	}

	public void setBajaBodegaBEAN(BajaBodega bajaBodegaBEAN) {
		this.bajaBodegaBEAN = bajaBodegaBEAN;
	}

	public List<Bodega> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(bodegaDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Bodega> lista) {
		this.lista = lista;
	}

	public List<Bodega> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Bodega> filtered) {
		this.filtered = filtered;
	}

	public SelectItem[] getAgencias() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Agencia> listaAgencias = new ArrayList<Agencia>();
		listaAgencias = agenciaDAO.find(attributes, values, order, index, size);
		this.agencias = new SelectItem[listaAgencias.size()];
		for (Agencia a : listaAgencias) {
			this.agencias[i] = new SelectItem(a.getId(), a.getNombre());
			i++;
		}
		return agencias;
	}

	public void setAgencias(SelectItem[] agencias) {
		this.agencias = agencias;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Bodega[] getSelectedBodegas() {
		return selectedBodegas;
	}

	public void setSelectedBodegas(Bodega[] selectedBodegas) {
		this.selectedBodegas = selectedBodegas;
	}

	public String onCrear() {
		return "crearBodega";
	}

	public void onModificar(RowEditEvent event) {

		Bodega bodega = new Bodega();
		bodega = (Bodega) event.getObject();
		bodega.setAgencia(agenciaDAO.read(Integer.parseInt(getAgencia())));
		modificarBodegaBEAN.setBodega(bodega);
		modificarBodegaBEAN.modificar();

	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Bodega cancelada",
				String.valueOf(((Bodega) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedBodegas.length > 0) {
			for (Bodega b : selectedBodegas) {
				bajaBodegaBEAN.setBodega(b);
				bajaBodegaBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas bodegas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodega";
	}

	public String onAlta() {
		if (selectedBodegas.length > 0) {
			for (Bodega b : selectedBodegas) {
				altaBodegaBEAN.setBodega(b);
				altaBodegaBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas bodegas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodega";
	}
}
