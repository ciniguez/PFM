package pfm.beans.bodega;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pfm.dao.BodegaDAO;
import pfm.entidades.Bodega;

@ManagedBean(name = "altaBodega")
@SessionScoped
public class AltaBodega implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDAO}")
	private BodegaDAO bodegaDAO;
	private Bodega bodega;

	public AltaBodega() {
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

	public String alta() {
		try {
			bodega.setEliminado(false);
			getBodegaDAO().update(bodega);
			FacesMessage msg = new FacesMessage("Bodega dada de alta",
					String.valueOf(bodega.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Bodega no dada de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarBodega";
	}
}
