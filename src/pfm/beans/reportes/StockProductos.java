package pfm.beans.reportes;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

import pfm.dao.AgenciaDAO;
import pfm.dao.CategoriaDAO;
import pfm.dao.EmpresaDAO;
import pfm.entidades.Agencia;
import pfm.entidades.Categoria;
import pfm.entidades.Empresa;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "stockProductos")
public class StockProductos {

	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	@ManagedProperty(value = "#{DAOFactory.agenciaDAO}")
	private AgenciaDAO agenciaDAO;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	private SelectItem[] empresas;
	private SelectItem[] agencias;
	private SelectItem[] categorias;
	private int empresa;
	private int agencia;
	private int categoria;	
	private String pathReportes = "rptStockProductos.jrxml";

	public StockProductos() {

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
		listaCategorias = categoriaDAO.find(attributes, values, order, index,
				size);
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

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
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
		parameters.put("parIdEmpresa", getEmpresa());
		parameters.put("parIdAgencia", getAgencia());
		parameters.put("parIdCategoria", getCategoria());
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
					+ "Stock de Productos" + "PDF");
			response.setHeader("Cache-Control", "max-age=30");
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			out.write(bytes);
			out.flush();
			out.close();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			System.out.println("ERROR JREXCEPTION: ");
			e.printStackTrace();
			response.setContentType("text/plain");
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("ERROR IOEXCEPTION: " + io.getMessage());
		} finally {
			// conn.close();
		}

	}
}
