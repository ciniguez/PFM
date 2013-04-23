package pfm.beans.empresa;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import pfm.dao.EmpresaDAO;
import pfm.entidades.Empresa;

@ManagedBean(name = "listarEmpresa")
@SessionScoped
public class ListarEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	@ManagedProperty(value = "#{modificarEmpresa}")
	private ModificarEmpresa modificarEmpresaBEAN;
	@ManagedProperty(value = "#{bajaEmpresa}")
	private BajaEmpresa bajaEmpresaBEAN;
	@ManagedProperty(value = "#{altaEmpresa}")
	private AltaEmpresa altaEmpresaBEAN;
	private List<Empresa> lista;
	private List<Empresa> filtered;
	private Empresa[] selectedEmpresas;

	public ListarEmpresa() {

	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public ModificarEmpresa getModificarEmpresaBEAN() {
		return modificarEmpresaBEAN;
	}

	public void setModificarEmpresaBEAN(ModificarEmpresa modificarEmpresaBEAN) {
		this.modificarEmpresaBEAN = modificarEmpresaBEAN;
	}

	public BajaEmpresa getBajaEmpresaBEAN() {
		return bajaEmpresaBEAN;
	}

	public void setBajaEmpresaBEAN(BajaEmpresa bajaEmpresaBEAN) {
		this.bajaEmpresaBEAN = bajaEmpresaBEAN;
	}

	public AltaEmpresa getAltaEmpresaBEAN() {
		return altaEmpresaBEAN;
	}

	public void setAltaEmpresaBEAN(AltaEmpresa altaEmpresaBEAN) {
		this.altaEmpresaBEAN = altaEmpresaBEAN;
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

	public Empresa[] getSelectedEmpresas() {
		return selectedEmpresas;
	}

	public void setSelectedEmpresas(Empresa[] selectedEmpresas) {
		this.selectedEmpresas = selectedEmpresas;
	}

	public String onCrear() {
		return "empresaCrear";
	}

	public void onModificar(RowEditEvent event) {
		modificarEmpresaBEAN.setEmpresa((Empresa) event.getObject());
		modificarEmpresaBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Empresa cancelada",
				String.valueOf(((Empresa) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedEmpresas.length > 0) {
			for (Empresa e : selectedEmpresas) {
				bajaEmpresaBEAN.setEmpresa(e);
				bajaEmpresaBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas empresas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpresa";
	}

	public String onAlta() {
		if (selectedEmpresas.length > 0) {
			for (Empresa e : selectedEmpresas) {
				altaEmpresaBEAN.setEmpresa(e);
				altaEmpresaBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas empresas");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpresa";
	}
}
