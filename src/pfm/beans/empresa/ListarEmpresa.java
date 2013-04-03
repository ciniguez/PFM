package pfm.beans.empresa;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.EmpresaDAO;
import pfm.entidades.Empresa;

@ManagedBean(name = "listarEmpresa")
public class ListarEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	private List<Empresa> lista;
	private List<Empresa> filtered;

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
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(empresaDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Empresa> lista) {
		this.lista = lista;
	}

	public List<Empresa> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Empresa> filtered) {
		this.filtered = filtered;
	}

	public void onEdit(RowEditEvent event) {
		try {
			Empresa empresa = new Empresa();
			empresa = (Empresa) event.getObject();
			empresaDAO.update(empresa);

			FacesMessage msg = new FacesMessage("Empresa actualizada",
					String.valueOf(((Empresa) event.getObject()).getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Empresa no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Empresa cancelada",
				String.valueOf(((Empresa) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
