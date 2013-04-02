package pfm.beans.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pfm.dao.EmpresaDAO;
import pfm.entidades.Empresa;

@ManagedBean(name = "listarEmpresa")
public class ListarEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	private List<Empresa> lista = new ArrayList<Empresa>();
	private List<Empresa> filtered = new ArrayList<Empresa>();

	public ListarEmpresa() {
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public List<Empresa> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "razonSocial";
		int index = -1;
		int size = -1;
		setLista(empresaDAO.find(attributes, values, order, index, size));		
		return lista;
	}

	public void setLista(List<Empresa> lista) {
		this.lista = lista;
	}

	public List<Empresa> getFiltered() {
		String[] attributes = {};
		String[] values = {};
		String order = "razonSocial";
		int index = -1;
		int size = -1;
		setFiltered(empresaDAO.find(attributes, values, order, index, size));
		return filtered;
	}

	public void setFiltered(List<Empresa> filtered) {
		this.filtered = filtered;
	}

}
