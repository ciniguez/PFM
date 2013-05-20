package pfm.beans.factura;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import pfm.entidades.Factura;
import pfm.jpa.JPADAOFactory;

@ManagedBean(name = "imprimirFactura")
public class ImprimirFactura implements Serializable {

	private static final long serialVersionUID = 1L;
	private String pathReportes = "/reportes/rptFactura.jasper";

	public ImprimirFactura() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirFactura(Factura f) {
		try {
			Map parameters = new HashMap();
			parameters.put("parIdFactura", f.getId());
			parameters.put("nombreEmpresa", f.getAgencia().getEmpresa()
					.getRazonSocial());
			parameters.put("direccionEmpresa", f.getAgencia().getEmpresa()
					.getDireccion());
			parameters.put("telefonoEmpresa", f.getAgencia().getEmpresa()
					.getTelefono());
			parameters.put("rucEmpresa", f.getAgencia().getEmpresa().getRuc());
			Connection connection = JPADAOFactory.getFactory().getAgenciaDAO()
					.getConexion();
			String reportPath = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath(pathReportes);
			JasperPrint jasperPrintFactura = JasperFillManager.fillReport(
					reportPath, parameters, connection);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition",
					"attachment; filename=factura.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse
					.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrintFactura,
					servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
