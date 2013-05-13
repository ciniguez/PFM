package pfm.beans.bodega;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.dao.BodegaDAO;
import pfm.entidades.Bodega;

@ManagedBean(name = "bajaBodega")
@RequestScoped
public class BajaBodega implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDAO}")
	private BodegaDAO bodegaDAO;
	private Bodega bodega;

	public BajaBodega() {
	}

	public BodegaDAO getBodegaDAO() {
		return bodegaDAO;
	}

	public void setBodegaDAO(BodegaDAO bodegaDAO) {
		this.bodegaDAO = bodegaDAO;
	}

	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	public String baja() {
		try {
			bodega.setEliminado(true);
			getBodegaDAO().update(bodega);
			FacesMessage msg = new FacesMessage("Bodega dada de baja",
					String.valueOf(bodega.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Bodega no dada de baja");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			FacesContext.getCurrentInstance().validationFailed();
		}
		return "listarBodega";
	}
}
