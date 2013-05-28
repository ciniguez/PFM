package pfm.beans.factura;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import pfm.entidades.Factura;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "imprimirFactura")
public class ImprimirFactura implements Serializable {

	private static final long serialVersionUID = 1L;
	private String pathReportes = "rptFactura.jrxml";

	public ImprimirFactura() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirFactura(Factura f) {

		Map parameters = new HashMap();
		parameters.put("parIdFactura", f.getId());
		parameters.put("nombreEmpresa", f.getAgencia().getEmpresa()
				.getRazonSocial());
		parameters.put("direccionEmpresa", f.getAgencia().getEmpresa()
				.getDireccion());
		parameters.put("telefonoEmpresa", f.getAgencia().getEmpresa()
				.getTelefono());
		parameters.put("rucEmpresa", f.getAgencia().getEmpresa().getRuc());
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
					+ "Factura" + "PDF");
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
