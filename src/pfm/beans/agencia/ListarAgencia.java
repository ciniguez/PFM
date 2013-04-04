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
	private List<Agencia> lista;
	private List<Agencia> filtered;
	private SelectItem[] empresas;
	private String empresa;

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

	public void onEdit(RowEditEvent event) {

		try {
			Agencia agencia = new Agencia();
			agencia = (Agencia) event.getObject();
			agencia.setEmpresa(empresaDAO.read(Integer.parseInt(getEmpresa())));
			agenciaDAO.update(agencia);
			FacesMessage msg = new FacesMessage("Agencia actualizada",
					String.valueOf(((Agencia) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			System.out.println(e);
			FacesMessage msg = new FacesMessage("Error",
					"Agencia no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Agencia cancelada",
				String.valueOf(((Agencia) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
