package pfm.beans.login;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "logoutUsuario")
@SessionScoped
public class LogoutUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public LogoutUsuario() {

	}

	public String logout() {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
				.getSession(false)).invalidate();
		return "logout";
	}
}
