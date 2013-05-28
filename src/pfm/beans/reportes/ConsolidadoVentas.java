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

import pfm.dao.EmpresaDAO;
import pfm.entidades.Empresa;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "consolidadoVentas")
public class ConsolidadoVentas implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	private String empresa;
	private SelectItem[] empresas;
	private Date fechaInicio;
	private Date fechaFin;
	private String pathReportes = "rptVentas.jrxml";

	public ConsolidadoVentas() {

	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
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
		parameters.put("parIdEmpresa", Integer.parseInt(empresa));
		parameters.put("parFechaInicio", fechaInicio);
		parameters.put("parFechaFin", fechaFin);

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
					+ "Consolidado de ventas" + "PDF");
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
