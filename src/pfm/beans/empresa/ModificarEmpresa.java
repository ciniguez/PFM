package pfm.beans.empresa;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pfm.dao.EmpresaDAO;

@ManagedBean(name = "modificarEmpresa")
public class ModificarEmpresa implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.empresaDAO}")
	private EmpresaDAO empresaDAO;

	public ModificarEmpresa() {

	}
}
