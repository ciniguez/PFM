package pfm.beans.categoria;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import pfm.dao.CategoriaDAO;
import pfm.entidades.Categoria;

@ManagedBean(name = "modificarCategoria")
public class ModificarCategoria implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.categoriaDAO}")
	private CategoriaDAO categoriaDAO;
	private Categoria categoria;

	public ModificarCategoria() {
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

	public String modificar() {
		try {
			getCategoriaDAO().update(categoria);
			FacesMessage msg = new FacesMessage("Categoria actualizada", String.valueOf(categoria.getId()));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Error", "Categoria no actualizada");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarCategoria";
	}
}