package pfm.beans.agencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pfm.dao.AgenciaDAO;
import pfm.dao.EmpresaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Empresa;

@ManagedBean(name = "crearAgencia")
public class CrearAgencia implements Serializable {

	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	private Agencia agencia = new Agencia();
	private SelectItem[] empresas;
	private String empresa;
	private static final long serialVersionUID = 1L;

	public CrearAgencia() {

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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
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

	public String crear() {
		try {
			agencia.setEmpresa(empresaDAO.read(Integer.parseInt(getEmpresa())));
			agenciaDAO.create(agencia);
			FacesMessage msg = new FacesMessage("Agencia creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Agencia no creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarAgencia";
	}
}
