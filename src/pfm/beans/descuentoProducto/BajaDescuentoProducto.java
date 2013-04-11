package pfm.beans.descuentoProducto;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.DescuentoProductoDAO;
import pfm.entidades.DescuentoProducto;

@ManagedBean(name = "bajaDescuentoProducto")
public class BajaDescuentoProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoProductoDAO}")
	private DescuentoProductoDAO descuentoProductoDAO;
	private DescuentoProducto descuentoProducto;

	public BajaDescuentoProducto() {

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

	public String baja() {
		try {
			descuentoProducto.setEliminado(true);
			getDescuentoProductoDAO().update(descuentoProducto);
			FacesMessage msg = new FacesMessage(
					"Descuento por Producto dado de baja",
					String.valueOf(descuentoProducto.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Descuento por Producto no dado de baja");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarDescuentoProducto";
	}
}
