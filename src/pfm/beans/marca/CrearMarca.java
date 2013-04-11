package pfm.beans.marca;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.MarcaDAO;
import pfm.entidades.Marca;

@ManagedBean(name = "crearMarca")
public class CrearMarca implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.marcaDAO}")
	private MarcaDAO marcaDAO;
	private Marca marca = new Marca();

	public CrearMarca() {

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

	public String crear() {
		try {
			marcaDAO.create(marca);
			FacesMessage msg = new FacesMessage("Marca creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Marca no creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarMarca";
	}

}