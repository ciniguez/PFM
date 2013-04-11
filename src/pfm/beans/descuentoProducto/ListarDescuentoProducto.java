package pfm.beans.descuentoProducto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.RowEditEvent;

import pfm.dao.DescuentoDAO;
import pfm.dao.DescuentoProductoDAO;
import pfm.dao.ProductoDAO;
import pfm.entidades.Descuento;
import pfm.entidades.DescuentoProducto;
import pfm.entidades.Producto;

@ManagedBean(name = "listarDescuentoProducto")
public class ListarDescuentoProducto implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.descuentoProductoDAO}")
	private DescuentoProductoDAO descuentoProductoDAO;
	@ManagedProperty(value = "#{modificarDescuentoProducto}")
	private ModificarDescuentoProducto modificarDescuentoProductoBEAN;
	@ManagedProperty(value = "#{bajaDescuentoProducto}")
	private BajaDescuentoProducto bajaDescuentoProductoBEAN;
	@ManagedProperty(value = "#{altaDescuentoProducto}")
	private AltaDescuentoProducto altaDescuentoProductoBEAN;
	@ManagedProperty(value = "#{DAOFactory.descuentoDAO}")
	private DescuentoDAO descuentoDAO;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	private List<DescuentoProducto> lista;
	private List<DescuentoProducto> filtered;
	private DescuentoProducto[] selectedDescuentoProducto;
	private SelectItem[] descuentos;
	private SelectItem[] productos;
	private String descuento;
	private String producto;

	public ListarDescuentoProducto() {
	}

	public DescuentoProductoDAO getDescuentoProductoDAO() {
		return descuentoProductoDAO;
	}

	public void setDescuentoProductoDAO(
			DescuentoProductoDAO descuentoProductoDAO) {
		this.descuentoProductoDAO = descuentoProductoDAO;
	}

	public ModificarDescuentoProducto getModificarDescuentoProductoBEAN() {
		return modificarDescuentoProductoBEAN;
	}

	public void setModificarDescuentoProductoBEAN(
			ModificarDescuentoProducto modificarDescuentoProductoBEAN) {
		this.modificarDescuentoProductoBEAN = modificarDescuentoProductoBEAN;
	}

	public BajaDescuentoProducto getBajaDescuentoProductoBEAN() {
		return bajaDescuentoProductoBEAN;
	}

	public void setBajaDescuentoProductoBEAN(
			BajaDescuentoProducto bajaDescuentoProductoBEAN) {
		this.bajaDescuentoProductoBEAN = bajaDescuentoProductoBEAN;
	}

	public AltaDescuentoProducto getAltaDescuentoProductoBEAN() {
		return altaDescuentoProductoBEAN;
	}

	public void setAltaDescuentoProductoBEAN(
			AltaDescuentoProducto altaDescuentoProductoBEAN) {
		this.altaDescuentoProductoBEAN = altaDescuentoProductoBEAN;
	}

	public DescuentoDAO getDescuentoDAO() {
		return descuentoDAO;
	}

	public void setDescuentoDAO(DescuentoDAO descuentoDAO) {
		this.descuentoDAO = descuentoDAO;
	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
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

	public DescuentoProducto[] getSelectedDescuentoProducto() {
		return selectedDescuentoProducto;
	}

	public void setSelectedDescuentoProducto(
			DescuentoProducto[] selectedDescuentoProducto) {
		this.selectedDescuentoProducto = selectedDescuentoProducto;
	}

	public SelectItem[] getDescuentos() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Descuento> listaDescuentos = new ArrayList<Descuento>();
		listaDescuentos = descuentoDAO.find(attributes, values, order, index,
				size);
		this.descuentos = new SelectItem[listaDescuentos.size()];
		for (Descuento d : listaDescuentos) {
			this.descuentos[i] = new SelectItem(d.getId(), d.getNombre());
			i++;
		}
		return descuentos;
	}

	public void setDescuentos(SelectItem[] descuentos) {
		this.descuentos = descuentos;
	}

	public SelectItem[] getProductos() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		int i = 0;

		List<Producto> listaProductos = new ArrayList<Producto>();
		listaProductos = productoDAO.find(attributes, values, order, index,
				size);
		this.productos = new SelectItem[listaProductos.size()];
		for (Producto p : listaProductos) {
			this.productos[i] = new SelectItem(p.getId(), p.getNombre());
			i++;
		}
		return productos;
	}

	public void setProductos(SelectItem[] productos) {
		this.productos = productos;
	}

	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String onCrear() {
		return "crearDescuentoProducto";
	}

	public void onModificar(RowEditEvent event) {
		DescuentoProducto d = new DescuentoProducto();
		d = (DescuentoProducto) event.getObject();
		d.setDescuento(descuentoDAO.read(Integer.parseInt(getDescuento())));
		d.setProducto(productoDAO.read(Integer.parseInt(getProducto())));
		modificarDescuentoProductoBEAN.setDescuentoProducto(d);
		modificarDescuentoProductoBEAN.modificar();

	}

	public void onCancel(RowEditEvent event) {

		FacesMessage msg = new FacesMessage("Descuento por Producto cancelada",
				String.valueOf(((DescuentoProducto) event.getObject()).getId()));

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onBaja() {
		if (selectedDescuentoProducto.length > 0) {
			for (DescuentoProducto d : selectedDescuentoProducto) {
				bajaDescuentoProductoBEAN.setDescuentoProducto(d);
				bajaDescuentoProductoBEAN.baja();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas descuentos por producto");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarDescuentoProducto";
	}

	public String onAlta() {
		if (selectedDescuentoProducto.length > 0) {
			for (DescuentoProducto d : selectedDescuentoProducto) {
				altaDescuentoProductoBEAN.setDescuentoProducto(d);
				altaDescuentoProductoBEAN.alta();
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar uno o mas descuentos por producto");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "listarDescuentoProducto";
	}
}
