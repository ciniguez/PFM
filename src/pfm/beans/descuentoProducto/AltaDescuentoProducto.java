package pfm.beans.descuentoProducto;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.DescuentoProductoDAO;
import pfm.entidades.DescuentoProducto;

@ManagedBean(name = "altaDescuentoProducto")
@RequestScoped
public class AltaDescuentoProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoProductoDAO}")
	private DescuentoProductoDAO descuentoProductoDAO;
	private DescuentoProducto descuentoProducto;

	public AltaDescuentoProducto() {

	}

	public DescuentoProductoDAO getDescuentoProductoDAO() {
		return descuentoProductoDAO;
	}

	public void setDescuentoProductoDAO(
			DescuentoProductoDAO descuentoProductoDAO) {
		this.descuentoProductoDAO = descuentoProductoDAO;
	}

	public DescuentoProducto getDescuentoProducto() {
		return descuentoProducto;
	}

	public void setDescuentoProducto(DescuentoProducto descuentoProducto) {
		this.descuentoProducto = descuentoProducto;
	}

	public String alta() {
		try {
			descuentoProducto.setEliminado(false);
			getDescuentoProductoDAO().update(descuentoProducto);
			FacesMessage msg = new FacesMessage(
					"Descuento por Producto dado de alta",
					String.valueOf(descuentoProducto.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Descuento por Producto no dado de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}

		return "listarDescuentoProducto";
	}
}
