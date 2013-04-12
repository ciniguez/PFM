package pfm.beans.empleadoAgencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pfm.dao.AgenciaDAO;
import pfm.dao.EmpleadoAgenciaDAO;
import pfm.dao.RolDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.Agencia;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Rol;
import pfm.entidades.Usuario;

@ManagedBean(name = "crearEmpleadoAgencia")
public class CrearEmpleadoAgencia implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empleadoAgenciaDAO}")
	private EmpleadoAgenciaDAO empleadoAgenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO empleadoDAO;
	@ManagedProperty(value = "#{DAOFactory.rolDAO}")
	private RolDAO rolDAO;
	private EmpleadoAgencia empleadoAgencia = new EmpleadoAgencia();
	private SelectItem[] agencias;
	private SelectItem[] empleados;
	private String agencia;
	private String empleado;

	public CrearEmpleadoAgencia() {

	}

	public EmpleadoAgenciaDAO getEmpleadoAgenciaDAO() {
		return empleadoAgenciaDAO;
	}

	public void setEmpleadoAgenciaDAO(EmpleadoAgenciaDAO empleadoAgenciaDAO) {
		this.empleadoAgenciaDAO = empleadoAgenciaDAO;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setRolDAO(RolDAO rolDAO) {
		this.rolDAO = rolDAO;
	}

	public EmpleadoAgencia getEmpleadoAgencia() {
		return empleadoAgencia;
	}

	public void setEmpleadoAgencia(EmpleadoAgencia empleadoAgencia) {
		this.empleadoAgencia = empleadoAgencia;
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
		rol = rolDAO.read(1);//el rol 1 debe ser de empleado
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

	public String crear() {
		try {
			empleadoAgencia.setAgencia(agenciaDAO.read(Integer
					.parseInt(getAgencia())));
			empleadoAgencia.setEmpleado(empleadoDAO.read(Integer
					.parseInt(getEmpleado())));
			empleadoAgenciaDAO.create(empleadoAgencia);
			FacesMessage msg = new FacesMessage("Empleado por agencia creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Empleado por agencia no creado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarEmpleadoAgencia";
	}

}
