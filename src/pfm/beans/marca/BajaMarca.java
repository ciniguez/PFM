package pfm.beans.marca;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.MarcaDAO;
import pfm.entidades.Marca;

@ManagedBean(name = "bajaMarca")
public class BajaMarca implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.marcaDAO}")
	private MarcaDAO marcaDAO;
	private Marca marca;

	public BajaMarca() {
	}

	public MarcaDAO getMarcaDAO() {
		return marcaDAO;
	}

	public void setMarcaDAO(MarcaDAO marcaDAO) {
		this.marcaDAO = marcaDAO;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public String baja() {
		try {
			marca.setEliminado(true);
			getMarcaDAO().update(marca);
			FacesMessage msg = new FacesMessage("Marca dada de baja",
					String.valueOf(marca.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error",
					"Marca no dada de baja");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarMarca";
	}
}
