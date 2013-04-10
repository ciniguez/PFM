package pfm.beans.bodegaDetalle;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.BodegaDetalleDAO;
import pfm.entidades.BodegaDetalle;

@ManagedBean(name = "bajaBodegaDetalle")
public class BajaBodegaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDetalleDAO}")
	private BodegaDetalleDAO bodegaDetalleDAO;
	private BodegaDetalle bodegaDetalle;

	public BajaBodegaDetalle() {
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

	public String baja() {
		try {
			bodegaDetalle.setEliminado(true);
			getBodegaDetalleDAO().update(bodegaDetalle);
			FacesMessage msg = new FacesMessage(
					"Producto por Bodega dado de baja",
					String.valueOf(bodegaDetalle.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Producto por Bodega no dado de baja");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodegaDetalle";
	}
}
