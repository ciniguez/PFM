package pfm.beans.factura;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pfm.dao.BodegaDetalleDAO;
import pfm.dao.DescuentoDAO;
import pfm.dao.DescuentoProductoDAO;
import pfm.dao.EmpresaDAO;
import pfm.dao.FacturaDetalleDAO;
import pfm.entidades.FacturaDetalle;

@ManagedBean(name = "modificarFacturaDetalle")
@SessionScoped
public class ModificarFacturaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.facturaDetalleDAO}")
	private FacturaDetalleDAO facturaDetalleDAO;
	@ManagedProperty(value = "#{DAOFactory.descuentoProductoDAO}")
	private DescuentoProductoDAO descuentoProductoDAO;
	@ManagedProperty(value = "#{DAOFactory.descuentoDAO}")
	private DescuentoDAO descuentoDAO;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;
	@ManagedProperty(value = "#{DAOFactory.bodegaDetalleDAO}")
	private BodegaDetalleDAO bodegaDetalleDAO;
	private FacturaDetalle facturaDetalle;

	public ModificarFacturaDetalle() {

	}

	public FacturaDetalleDAO getFacturaDetalleDAO() {
		return facturaDetalleDAO;
	}

	public void setFacturaDetalleDAO(FacturaDetalleDAO facturaDetalleDAO) {
		this.facturaDetalleDAO = facturaDetalleDAO;
	}

	public DescuentoProductoDAO getDescuentoProductoDAO() {
		return descuentoProductoDAO;
	}

	public void setDescuentoProductoDAO(
			DescuentoProductoDAO descuentoProductoDAO) {
		this.descuentoProductoDAO = descuentoProductoDAO;
	}

	public DescuentoDAO getDescuentoDAO() {
		return descuentoDAO;
	}

	public void setDescuentoDAO(DescuentoDAO descuentoDAO) {
		this.descuentoDAO = descuentoDAO;
	}

	public EmpresaDAO getEmpresaDAO() {
		return empresaDAO;
	}

	public void setEmpresaDAO(EmpresaDAO empresaDAO) {
		this.empresaDAO = empresaDAO;
	}

	public BodegaDetalleDAO getBodegaDetalleDAO() {
		return bodegaDetalleDAO;
	}

	public void setBodegaDetalleDAO(BodegaDetalleDAO bodegaDetalleDAO) {
		this.bodegaDetalleDAO = bodegaDetalleDAO;
	}

	public FacturaDetalle getFacturaDetalle() {
		return facturaDetalle;
	}

	public void setFacturaDetalle(FacturaDetalle facturaDetalle) {
		this.facturaDetalle = facturaDetalle;
	}

	public String modificar() {
		try {
			int idDescuento = 0;
			double valorDescuento = 0;
			double valorIva = 0;
			double precio = 0;

			//actualiza el precio 
			precio = bodegaDetalleDAO.getPrecioByBodegaDetalle(facturaDetalle
					.getBodegaDetalle().getId(), false);
			getFacturaDetalleDAO().setPrecioByBodegaDetalle(facturaDetalle,
					precio);

			//obtiene el descuento del producto
			idDescuento = descuentoProductoDAO.getDescuentoId(facturaDetalle
					.getBodegaDetalle().getProducto(), false);
			valorDescuento = descuentoDAO.getValorDescuentoByFecha(idDescuento,
					false);
			
			//obtiene el iva de la empresa
			valorIva = empresaDAO.getIvaByEmpresa(facturaDetalle
					.getBodegaDetalle().getBodega().getAgencia().getEmpresa()
					.getId(), false);

			//genera los totales en la facturaDetalle
			getFacturaDetalleDAO().setTotalesFacturaDetalle(facturaDetalle,
					valorDescuento, valorIva);
			getFacturaDetalleDAO().update(facturaDetalle);

			FacesMessage msg = new FacesMessage("Producto actualizado",
					String.valueOf(facturaDetalle.getBodegaDetalle()
							.getProducto()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Producto no actualizado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarFacturaDetalle";
	}
}
