package pfm.beans.bodegaDetalle;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.BodegaDetalleDAO;
import pfm.entidades.BodegaDetalle;

@ManagedBean(name = "modificarBodegaDetalle")
@RequestScoped
public class ModificarBodegaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDetalleDAO}")
	private BodegaDetalleDAO bodegaDetalleDAO;
	private BodegaDetalle bodegaDetalle;

	public ModificarBodegaDetalle() {
	}

	public BodegaDetalleDAO getBodegaDetalleDAO() {
		return bodegaDetalleDAO;
	}

	public void setBodegaDetalleDAO(BodegaDetalleDAO bodegaDetalleDAO) {
		this.bodegaDetalleDAO = bodegaDetalleDAO;
	}

	public BodegaDetalle getBodegaDetalle() {
		return bodegaDetalle;
	}

	public void setBodegaDetalle(BodegaDetalle bodegaDetalle) {
		this.bodegaDetalle = bodegaDetalle;
	}

	public String modificar() {
		try {
			getBodegaDetalleDAO().update(bodegaDetalle);
			FacesMessage msg = new FacesMessage(
					"Producto por Bodega actualizado",
					String.valueOf(bodegaDetalle.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Producto por Bodega no actualizado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}
		return "listarBodegaDetalle";
	}
}
