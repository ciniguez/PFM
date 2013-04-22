package pfm.beans.empleadoAgencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import pfm.dao.AgenciaDAO;
import pfm.dao.EmpleadoAgenciaDAO;
import pfm.dao.RolDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.Agencia;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Rol;
import pfm.entidades.Usuario;

@ManagedBean(name = "listarEmpleadoAgencia")
@SessionScoped
public class ListarEmpleadoAgencia implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empleadoAgenciaDAO}")
	private EmpleadoAgenciaDAO empleadoAgenciaDAO;
	@ManagedProperty(value = "#{modificarEmpleadoAgencia}")
	private ModificarEmpleadoAgencia modificarEmpleadoAgenciaBEAN;
	@ManagedProperty(value = "#{altaEmpleadoAgencia}")
	private AltaEmpleadoAgencia altaEmpleadoAgenciaBEAN;
	@ManagedProperty(value = "#{bajaEmpleadoAgencia}")
	private BajaEmpleadoAgencia bajaEmpleadoAgenciaBEAN;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO empleadoDAO;
	@ManagedProperty(value = "#{DAOFactory.rolDAO}")
	private RolDAO rolDAO;
	private List<EmpleadoAgencia> lista;
	private List<EmpleadoAgencia> filtered;
	private SelectItem[] agencias;
	private SelectItem[] empleados;
	private String agencia;
	private String empleado;
	private EmpleadoAgencia[] selectedEmpleadoAgencia;

	public ListarEmpleadoAgencia() {

	}

	public EmpleadoAgenciaDAO getEmpleadoAgenciaDAO() {
		return empleadoAgenciaDAO;
	}

	public void setEmpleadoAgenciaDAO(EmpleadoAgenciaDAO empleadoAgenciaDAO) {
		this.empleadoAgenciaDAO = empleadoAgenciaDAO;
	}

	public ModificarEmpleadoAgencia getModificarEmpleadoAgenciaBEAN() {
		return modificarEmpleadoAgenciaBEAN;
	}

	public void setModificarEmpleadoAgenciaBEAN(
			ModificarEmpleadoAgencia modificarEmpleadoAgenciaBEAN) {
		this.modificarEmpleadoAgenciaBEAN = modificarEmpleadoAgenciaBEAN;
	}

	public AltaEmpleadoAgencia getAltaEmpleadoAgenciaBEAN() {
		return altaEmpleadoAgenciaBEAN;
	}

	public void setAltaEmpleadoAgenciaBEAN(
			AltaEmpleadoAgencia altaEmpleadoAgenciaBEAN) {
		this.altaEmpleadoAgenciaBEAN = altaEmpleadoAgenciaBEAN;
	}

	public BajaEmpleadoAgencia getBajaEmpleadoAgenciaBEAN() {
		return bajaEmpleadoAgenciaBEAN;
	}

	public void setBajaEmpleadoAgenciaBEAN(
			BajaEmpleadoAgencia bajaEmpleadoAgenciaBEAN) {
		this.bajaEmpleadoAgenciaBEAN = bajaEmpleadoAgenciaBEAN;
	}

	public AgenciaDAO getAgenciaDAO() {
		return agenciaDAO;
	}

	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}

	public UsuarioDAO getEmpleadoDAO() {
		return empleadoDAO;
	}

	public void setEmpleadoDAO(UsuarioDAO empleadoDAO) {
		this.empleadoDAO = empleadoDAO;
	}

	public RolDAO getRolDAO() {
		return rolDAO;
	}

	public void setRolDAO(RolDAO rolDAO) {
		this.rolDAO = rolDAO;
	}

	public List<EmpleadoAgencia> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;

		setLista(empleadoAgenciaDAO
				.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<EmpleadoAgencia> lista) {
		this.lista = lista;
	}

	public List<EmpleadoAgencia> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<EmpleadoAgencia> filtered) {
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

	public SelectItem[] getEmpleados() {
		int i = 0;
		Rol rol = new Rol();
		rol = rolDAO.read(1);// el rol 1 debe ser de empleado
		List<Usuario> listaEmpleados = new ArrayList<Usuario>();
		listaEmpleados = empleadoDAO.getEmpleado(rol, false);
		this.empleados = new SelectItem[listaEmpleados.size()];
		for (Usuario e : listaEmpleados) {
			this.empleados[i] = new SelectItem(e.getId(), e.toString());
			i++;
		}
		return empleados;
	}

	public void setEmpleados(SelectItem[] empleados) {
		this.empleados = empleados;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public EmpleadoAgencia[] getSelectedEmpleadoAgencia() {
		return selectedEmpleadoAgencia;
	}

	public void setSelectedEmpleadoAgencia(
			EmpleadoAgencia[] selectedEmpleadoAgencia) {
		this.selectedEmpleadoAgencia = selectedEmpleadoAgencia;
	}

	public String onCrear() {

		return "crearEmpleadoAgencia";
	}

	public void onModificar(RowEditEvent event) {
		EmpleadoAgencia e = new EmpleadoAgencia();
		e = (EmpleadoAgencia) event.getObject();
		e.setAgencia(agenciaDAO.read(Integer.parseInt(getAgencia())));
		e.setEmpleado(empleadoDAO.read(Integer.parseInt(getEmpleado())));
		modificarEmpleadoAgenciaBEAN.setEmpleadoAgencia(e);
		modificarEmpleadoAgenciaBEAN.modificar();
	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Empleado por agencia cancelado",
				String.valueOf(((EmpleadoAgencia) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedEmpleadoAgencia.length > 0) {
			for (EmpleadoAgencia e : selectedEmpleadoAgencia) {
				bajaEmpleadoAgenciaBEAN.setEmpleadoAgencia(e);
				bajaEmpleadoAgenciaBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas empleados por agencia");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpleadoAgencia";
	}

	public String onAlta() {
		if (selectedEmpleadoAgencia.length > 0) {
			for (EmpleadoAgencia e : selectedEmpleadoAgencia) {
				altaEmpleadoAgenciaBEAN.setEmpleadoAgencia(e);
				altaEmpleadoAgenciaBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas empleados por agencia");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpleadoAgencia";
	}
}
