package pfm.beans.menu;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "menu")
@RequestScoped
public class Menu {
	
	public String listarEmpresa(){
		return "menu_listaEmpresa";
	}
	public String listarAgencia(){
		return "menu_listaAgencia";
	}

}
