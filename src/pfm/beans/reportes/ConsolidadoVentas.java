package pfm.beans.reportes;

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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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
	private JasperPrint jasperPrint;
	private String pathReportes = "/reportes/rptVentas.jasper";

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

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public String getPathReportes() {
		return pathReportes;
	}

	public void setPathReportes(String pathReportes) {
		this.pathReportes = pathReportes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init() throws Exception {
		Map parameters = new HashMap();
		parameters.put("parIdEmpresa", Integer.parseInt(empresa));
		parameters.put("parFechaInicio", fechaInicio);
		parameters.put("parFechaFin", fechaFin);
		Connection connection = JPADAOFactory.getFactory().getAgenciaDAO()
				.getConexion();
		String reportPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath(pathReportes);
		jasperPrint = JasperFillManager.fillReport(reportPath, parameters,
				connection);
	}

	public void generarPDF(ActionEvent event) {
		try {
			init();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition",
					"attachment; filename=report.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse
					.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,
					servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());

		}

	}
}
