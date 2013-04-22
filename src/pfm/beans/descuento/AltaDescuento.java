package pfm.beans.descuento;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pfm.dao.DescuentoDAO;
import pfm.entidades.Descuento;

@ManagedBean(name = "altaDescuento")
@SessionScoped
public class AltaDescuento implements Serializable {

	private static final long serialVersionUID = 1L;	
	@ManagedProperty(value = "#{DAOFactory.descuentoDAO}")
	private DescuentoDAO descuentoDAO;
	private Descuento descuento;

	public AltaDescuento() {

	}

	public DescuentoDAO getDescuentoDAO() {
		return descuentoDAO;
	}

	public void setDescuentoDAO(DescuentoDAO descuentoDAO) {
		this.descuentoDAO = descuentoDAO;
	}

	public Descuento getDescuento() {
		return descuento;
	}

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}

	public String alta() {

		try {
			descuento.setEliminado(false);
			getDescuentoDAO().update(descuento);
			FacesMessage msg = new FacesMessage("Descuento dado de alta",
					String.valueOf(descuento.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Descuento no dado de alta");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarDescuento";
	}
}
