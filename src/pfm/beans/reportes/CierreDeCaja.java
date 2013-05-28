package pfm.beans.reportes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import pfm.dao.EmpleadoAgenciaDAO;
import pfm.dao.UsuarioDAO;
import pfm.entidades.EmpleadoAgencia;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "cierreDeCaja")
public class CierreDeCaja implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.usuarioDAO}")
	private UsuarioDAO empleadoDAO;
	@ManagedProperty(value = "#{DAOFactory.empleadoAgenciaDAO}")
	private EmpleadoAgenciaDAO empleadoAgenciaDAO;
	private Usuario empleado;
	private String empleadoAgencia;
	private SelectItem[] empleadosAgencia;
	private Date fecha;
	private String pathReportes = "rptVentaCaja.jrxml";

	public CierreDeCaja() {

	}

	public UsuarioDAO getEmpleadoDAO() {
		return empleadoDAO;
	}

	public void setEmpleadoDAO(UsuarioDAO empleadoDAO) {
		this.empleadoDAO = empleadoDAO;
	}

	public EmpleadoAgenciaDAO getEmpleadoAgenciaDAO() {
		return empleadoAgenciaDAO;
	}

	public void setEmpleadoAgenciaDAO(EmpleadoAgenciaDAO empleadoAgenciaDAO) {
		this.empleadoAgenciaDAO = empleadoAgenciaDAO;
	}

	public String getEmpleadoAgencia() {
		return empleadoAgencia;
	}

	public void setEmpleadoAgencia(String empleadoAgencia) {
		this.empleadoAgencia = empleadoAgencia;
	}

	public SelectItem[] getEmpleadosAgencia() {
		if (getEmpleado().getRol().getId() == 1) {
			EmpleadoAgencia e = empleadoAgenciaDAO
					.getAgenciaByEmpleado(empleadoDAO.read(getEmpleado()
							.getId()));
			empleadosAgencia = new SelectItem[1];
			empleadosAgencia[0] = new SelectItem(e.getId(), e.getEmpleado()
					.toString());
		} else {
			String[] attributes = { "eliminado" };
			String[] values = { "0" };
			String order = "id";
			int index = -1;
			int size = -1;
			int i = 0;

			List<EmpleadoAgencia> listaEmpleadosAgencia = new ArrayList<EmpleadoAgencia>();
			listaEmpleadosAgencia = empleadoAgenciaDAO.find(attributes, values,
					order, index, size);
			empleadosAgencia = new SelectItem[listaEmpleadosAgencia.size()];
			for (EmpleadoAgencia e : listaEmpleadosAgencia) {
				empleadosAgencia[i] = new SelectItem(e.getId(), e.getEmpleado()
						.toString());
				i++;
			}
		}

		return empleadosAgencia;
	}

	public void setEmpleadosAgencia(SelectItem[] empleadosAgencia) {
		this.empleadosAgencia = empleadosAgencia;
	}

	public Usuario getEmpleado() {
		setEmpleado((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("UsuarioBean"));
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getPathReportes() {
		return pathReportes;
	}

	public void setPathReportes(String pathReportes) {
		this.pathReportes = pathReportes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void generarPDF(ActionEvent event) {
		Map parameters = new HashMap();
		parameters.put("parIdEmpleadoAgencia",
				Integer.parseInt(empleadoAgencia));
		parameters.put("parFechaActual", fecha);
		JasperPrint print = null;
		HttpServletResponse response = null;
		OutputStream out = null;
		FacesContext context = null;
		try {

			context = FacesContext.getCurrentInstance();
			response = (HttpServletResponse) context.getExternalContext()
					.getResponse();
			out = response.getOutputStream();

			InputStream is = this.getClass().getResourceAsStream(pathReportes);
			JasperDesign masterDesign = JRXmlLoader.load(is);
			JasperReport masterReport = JasperCompileManager
					.compileReport(masterDesign);

			Connection connection = JPADAOFactory.getFactory().getAgenciaDAO()
					.getConexion();
			print = JasperFillManager.fillReport(masterReport, parameters,
					connection);

			byte[] bytes = JasperExportManager.exportReportToPdf(print);
			response.setHeader("Content-disposition", "attachment; filename="
					+ "Cierre de Caja" + "PDF");
			response.setHeader("Cache-Control", "max-age=30");
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();
			context.responseComplete();
		} catch (JRException e) {
			response.setContentType("text/plain");
		} catch (IOException io) {
			System.out.println("ERROR IO: " + io.getMessage());
		} finally {
			// conn.close();
		}
	}
}
