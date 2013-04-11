package pfm.beans.descuento;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.DescuentoDAO;
import pfm.entidades.Descuento;

@ManagedBean(name = "crearDescuento")
public class CrearDescuento implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoDAO}")
	private DescuentoDAO descuentoDAO;
	private Descuento descuento = new Descuento();

	public CrearDescuento() {

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

	public String crear() {
		if (descuento.getFechaInicio().before(descuento.getFechaFin())
				|| descuento.getFechaInicio().equals(descuento.getFechaFin())) {
			try {
				descuentoDAO.create(descuento);
				FacesMessage msg = new FacesMessage("Descuento creado");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Error",
						"Descuento no creado");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			return "listarDescuento";
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"La fecha de inicio debe ser menor a la de fin");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return "crearDesccuento";
		}

	}

}
