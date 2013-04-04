
package pfm.beans.categoria;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.CategoriaDAO;
import pfm.entidades.Categoria;

@ManagedBean(name = "altaCategoria")
public class AltaCategoria implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	private Categoria categoria = new Categoria();

	public AltaCategoria() {

	}

	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String create() {
		try {
			categoriaDAO.create(categoria);
			FacesMessage msg = new FacesMessage("Categoria creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Categoria no creada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "listarCategoria";
	}

}
