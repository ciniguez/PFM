package pfm.beans.reportes;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import pfm.dao.AgenciaDAO;
import pfm.dao.CategoriaDAO;
import pfm.dao.EmpresaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Categoria;
import pfm.entidades.Empresa;
import pfm.entidades.Factura;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "reporte")
@SessionScoped
public class ReporteMenu {

	private JasperPrint jasperPrint;
	private int idEmpresa;
	private int idAgencia;
	private int idCategoria;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	private SelectItem[] empresas;
	private SelectItem[] agencias;
	private SelectItem[] categorias;

	private String[] pathReportes = new String[] { "/reportes/rptStockProductos.jasper", "/reportes/rptFactura.jasper" };

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init() throws Exception {
		Map parameters = new HashMap();
		parameters.put("parIdEmpresa", this.getIdEmpresa());
		parameters.put("parIdAgencia", this.getIdAgencia());
		parameters.put("parIdCategoria", this.getIdCategoria());
		Connection connection = JPADAOFactory.getFactory().getAgenciaDAO().getConexion();
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(this.pathReportes[0]);
		jasperPrint = JasperFillManager.fillReport(reportPath, parameters, connection);
	}

	public void generarPDF(ActionEvent actionEvent) {
		try {
			init();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirFactura(Factura f) {
		try {
			Empresa e = f.getAgencia().getEmpresa();
			
			Map parameters = new HashMap();
			parameters.put("parIdFactura", f.getId());
			parameters.put("nombreEmpresa", e.getRazonSocial());
			parameters.put("direccionEmpresa", e.getDireccion());
			parameters.put("telefonoEmpresa", e.getTelefono());
			parameters.put("rucEmpresa", e.getRuc());
			Connection connection = JPADAOFactory.getFactory().getAgenciaDAO().getConexion();
			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(this.pathReportes[1]);
			JasperPrint jasperPrintFactura = JasperFillManager.fillReport(reportPath, parameters, connection);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=factura.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrintFactura, servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public int getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
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
		for (Agencia e : listaAgencias) {
			this.agencias[i] = new SelectItem(e.getId(), e.getNombre());
			i++;
		}
		return agencias;
	}

	public void setAgencias(SelectItem[] agencias) {
		this.agencias = agencias;
	}

	public SelectItem[] getCategorias() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Categoria> listaCategorias = new ArrayList<Categoria>();
		listaCategorias = categoriaDAO.find(attributes, values, order, index, size);
		this.categorias = new SelectItem[listaCategorias.size()];
		for (Categoria e : listaCategorias) {
			this.categorias[i] = new SelectItem(e.getId(), e.getNombre());
			i++;
		}
		return categorias;
	}

	public void setCategorias(SelectItem[] categorias) {
		this.categorias = categorias;
	}

	public AgenciaDAO getAgenciaDAO() {
		return agenciaDAO;
	}

	public void setAgenciaDAO(AgenciaDAO agenciaDAO) {
		this.agenciaDAO = agenciaDAO;
	}

	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

}
