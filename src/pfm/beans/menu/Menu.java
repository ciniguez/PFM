package pfm.beans.menu;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.entidades.Usuario;

@ManagedBean(name = "menu")
@RequestScoped
public class Menu {

	public boolean getIsAdmin() {
		Usuario usuarioSesion = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("UsuarioBean");
		if (usuarioSesion.getRol().getId() == 3)
			return true;
		else
			return false;

	}

}
