package pfm.beans.descuentoProducto;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pfm.dao.DescuentoProductoDAO;
import pfm.entidades.DescuentoProducto;

@ManagedBean(name = "listarDescuentoProducto")
public class ListarDescuentoProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoProductoDAO}")
	private DescuentoProductoDAO descuentoProductoDAO;
	private List<DescuentoProducto> lista;
	private List<DescuentoProducto> filtered;

	public ListarDescuentoProducto() {
	}

	public DescuentoProductoDAO getDescuentoProductoDAO() {
		return descuentoProductoDAO;
	}

	public void setDescuentoProductoDAO(
			DescuentoProductoDAO descuentoProductoDAO) {
		this.descuentoProductoDAO = descuentoProductoDAO;
	}

	public List<DescuentoProducto> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(descuentoProductoDAO.find(attributes, values, order, index,
				size));
		return lista;
	}

	public void setLista(List<DescuentoProducto> lista) {
		this.lista = lista;
	}

	public List<DescuentoProducto> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<DescuentoProducto> filtered) {
		this.filtered = filtered;
	}

}
