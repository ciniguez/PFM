package pfm.beans.empresa;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pfm.dao.EmpresaDAO;
import pfm.entidades.Empresa;

@ManagedBean(name = "altaEmpresa")
@SessionScoped
public class AltaEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	private Empresa empresa;

	public AltaEmpresa() {
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String alta() {
		try {
			empresa.setEliminado(false);
			getEmpresaDAO().update(empresa);
			FacesMessage msg = new FacesMessage("Empresa dada de alta",
					String.valueOf(empresa.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Empresa no dada de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpresa";
	}
}
