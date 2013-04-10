package pfm.beans.bodega;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pfm.dao.AgenciaDAO;
import pfm.dao.BodegaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Bodega;

@ManagedBean(name = "crearBodega")
public class CrearBodega implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDAO}")
	private BodegaDAO bodegaDAO;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	private Bodega bodega = new Bodega();
	private SelectItem[] agencias;
	private String agencia;

	public CrearBodega() {
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

	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
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

	public String crear() {
		try {
			bodega.setAgencia(agenciaDAO.read(Integer.parseInt(getAgencia())));
			bodegaDAO.create(bodega);

			FacesMessage msg = new FacesMessage("Bodega creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {

			FacesMessage msg = new FacesMessage("Error", "Bodega no creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodega";
	}

}
