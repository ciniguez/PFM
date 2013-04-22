package pfm.beans.bodegaDetalle;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pfm.dao.BodegaDetalleDAO;
import pfm.entidades.BodegaDetalle;

@ManagedBean(name = "altaBodegaDetalle")
@SessionScoped
public class AltaBodegaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDetalleDAO}")
	private BodegaDetalleDAO bodegaDetalleDAO;
	private BodegaDetalle bodegaDetalle;

	public AltaBodegaDetalle() {
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

	public String alta() {
		try {
			bodegaDetalle.setEliminado(false);
			getBodegaDetalleDAO().update(bodegaDetalle);
			FacesMessage msg = new FacesMessage(
					"Producto por Bodega dado de alta",
					String.valueOf(bodegaDetalle.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Producto por Bodega no dado de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodegaDetalle";
	}
}
