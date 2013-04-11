package pfm.beans.agencia;

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
import pfm.dao.EmpresaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Empresa;

@ManagedBean(name = "listarAgencia")
public class ListarAgencia implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	@ManagedProperty(value = "#{modificarAgencia}")
	private ModificarAgencia modificarAgenciaBEAN;
	@ManagedProperty(value = "#{altaAgencia}")
	private AltaAgencia altaAgenciaBEAN;
	@ManagedProperty(value = "#{bajaAgencia}")
	private BajaAgencia bajaAgenciaBEAN;
	private List<Agencia> lista;
	private List<Agencia> filtered;
	private SelectItem[] empresas;
	private String empresa;
	private Agencia[] selectedAgencias;

	public ListarAgencia() {

	}

	public AgenciaDAO getAgenciaDAO() {
		return agenciaDAO;
	}

	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public ModificarAgencia getModificarAgenciaBEAN() {
		return modificarAgenciaBEAN;
	}

	public void setModificarAgenciaBEAN(ModificarAgencia modificarAgenciaBEAN) {
		this.modificarAgenciaBEAN = modificarAgenciaBEAN;
	}

	public AltaAgencia getAltaAgenciaBEAN() {
		return altaAgenciaBEAN;
	}

	public void setAltaAgenciaBEAN(AltaAgencia altaAgenciaBEAN) {
		this.altaAgenciaBEAN = altaAgenciaBEAN;
	}

	public BajaAgencia getBajaAgenciaBEAN() {
		return bajaAgenciaBEAN;
	}

	public void setBajaAgenciaBEAN(BajaAgencia bajaAgenciaBEAN) {
		this.bajaAgenciaBEAN = bajaAgenciaBEAN;
	}

	public List<Agencia> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(agenciaDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Agencia> lista) {
		this.lista = lista;
	}

	public List<Agencia> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Agencia> filtered) {
		this.filtered = filtered;
	}

	public SelectItem[] getEmpresas() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Empresa> listaEmpresas = new ArrayList<Empresa>();
		listaEmpresas = empresaDAO.find(attributes, values, order, index, size);
		this.empresas = new SelectItem[listaEmpresas.size()];
		for (Empresa e : listaEmpresas) {
			this.empresas[i] = new SelectItem(e.getId(), e.getRazonSocial());
			i++;
		}
		return empresas;
	}

	public void setEmpresas(SelectItem[] empresas) {
		this.empresas = empresas;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Agencia[] getSelectedAgencias() {
		return selectedAgencias;
	}

	public void setSelectedAgencias(Agencia[] selectedAgencias) {
		this.selectedAgencias = selectedAgencias;
	}

	public String onCrear() {
		return "crearAgencia";
	}

	public void onModificar(RowEditEvent event) {

		Agencia agencia = new Agencia();
		agencia = (Agencia) event.getObject();
		agencia.setEmpresa(empresaDAO.read(Integer.parseInt(getEmpresa())));
		modificarAgenciaBEAN.setAgencia(agencia);
		modificarAgenciaBEAN.modificar();

	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Agencia cancelada",
				String.valueOf(((Agencia) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedAgencias.length > 0) {
			for (Agencia a : selectedAgencias) {
				bajaAgenciaBEAN.setAgencia(a);
				bajaAgenciaBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas agencias");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpresa";
	}

	public String onAlta() {
		if (selectedAgencias.length > 0) {
			for (Agencia a : selectedAgencias) {
				altaAgenciaBEAN.setAgencia(a);
				altaAgenciaBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas agencias");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpresa";
	}
}
