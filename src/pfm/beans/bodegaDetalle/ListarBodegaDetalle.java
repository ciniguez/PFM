package pfm.beans.bodegaDetalle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import pfm.dao.BodegaDAO;
import pfm.dao.BodegaDetalleDAO;
import pfm.dao.ProductoDAO;
import pfm.entidades.Bodega;
import pfm.entidades.BodegaDetalle;
import pfm.entidades.Producto;

@ManagedBean(name = "listarBodegaDetalle")
public class ListarBodegaDetalle implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.bodegaDetalleDAO}")
	private BodegaDetalleDAO bodegaDetalleDAO;
	@ManagedProperty(value = "#{DAOFactory.bodegaDAO}")
	private BodegaDAO bodegaDAO;
	@ManagedProperty(value = "#{DAOFactory.productoDAO}")
	private ProductoDAO productoDAO;
	private List<Bodega> listaBodegas;
	private List<Producto> listaProductos;
	private List<Bodega> filteredBodegas;
	private List<Producto> filteredProductos;
	private Bodega selectedBodega = new Bodega();
	private Producto selectedProducto = new Producto();
	private BodegaDetalle bodegaDetalle = new BodegaDetalle();

	public ListarBodegaDetalle() {
	}

	public BodegaDetalleDAO getBodegaDetalleDAO() {
		return bodegaDetalleDAO;
	}

	public void setBodegaDetalleDAO(BodegaDetalleDAO bodegaDetalleDAO) {
		this.bodegaDetalleDAO = bodegaDetalleDAO;
	}

	public BodegaDAO getBodegaDAO() {
		return bodegaDAO;
	}

	public void setBodegaDAO(BodegaDAO bodegaDAO) {
		this.bodegaDAO = bodegaDAO;
	}

	public ProductoDAO getProductoDAO() {
		return productoDAO;
	}

	public void setProductoDAO(ProductoDAO productoDAO) {
		this.productoDAO = productoDAO;
	}

	public List<Bodega> getListaBodegas() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		setListaBodegas(bodegaDAO.find(attributes, values, order, index, size));
		return listaBodegas;
	}

	public void setListaBodegas(List<Bodega> listaBodegas) {
		this.listaBodegas = listaBodegas;
	}

	public List<Producto> getListaProductos() {
		String[] attributes = { "eliminado" };
		String[] values = { "0" };
		String order = "id";
		int index = -1;
		int size = -1;
		setListaProductos(productoDAO.find(attributes, values, order, index,
				size));
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<Bodega> getFilteredBodegas() {
		return filteredBodegas;
	}

	public void setFilteredBodegas(List<Bodega> filteredBodegas) {
		this.filteredBodegas = filteredBodegas;
	}

	public List<Producto> getFilteredProductos() {
		return filteredProductos;
	}

	public void setFilteredProductos(List<Producto> filteredProductos) {
		this.filteredProductos = filteredProductos;
	}

	public Bodega getSelectedBodega() {
		return selectedBodega;
	}

	public void setSelectedBodega(Bodega selectedBodega) {
		this.selectedBodega = selectedBodega;
	}

	public Producto getSelectedProducto() {
		return selectedProducto;
	}

	public void setSelectedProducto(Producto selectedProducto) {
		this.selectedProducto = selectedProducto;
	}

	public BodegaDetalle getBodegaDetalle() {
		return bodegaDetalle;
	}

	public void setBodegaDetalle(BodegaDetalle bodegaDetalle) {
		this.bodegaDetalle = bodegaDetalle;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg;
		if (event.getObject().getClass() == Bodega.class) {
			msg = new FacesMessage("Bodega Seleccionada",
					((Bodega) event.getObject()).getNombre());

		} else {
			msg = new FacesMessage("Producto Seleccionado",
					((Producto) event.getObject()).getNombre());
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void buscarBodegaDetalle() {

		List<BodegaDetalle> lista = new ArrayList<BodegaDetalle>();
		lista = bodegaDetalleDAO.getBodegaDetalleByBodegaAndProducto(
				selectedProducto, selectedBodega);
		if (lista.size() > 0) {
			bodegaDetalle = lista.get(0);
		} else {
			bodegaDetalle = new BodegaDetalle();
		}
	}

	public boolean existeBodegaDetalle() {
		List<BodegaDetalle> lista = new ArrayList<BodegaDetalle>();
		lista = bodegaDetalleDAO.getBodegaDetalleByBodegaAndProducto(
				selectedProducto, selectedBodega);
		if (lista.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String create() {
		if (selectedProducto != null && selectedBodega != null) {
			if (existeBodegaDetalle()) {
				// se debe actualizar
				try {
					List<BodegaDetalle> lista = new ArrayList<BodegaDetalle>();
					lista = bodegaDetalleDAO
							.getBodegaDetalleByBodegaAndProducto(
									selectedProducto, selectedBodega);
					BodegaDetalle bod = new BodegaDetalle();
					bod = lista.get(0);
					bod.setCantidad(bodegaDetalle.getCantidad());
					bod.setPrecio(bodegaDetalle.getPrecio());

					bodegaDetalleDAO.update(bod);

					FacesMessage msg = new FacesMessage("Detalle actualizado");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
					FacesMessage msg = new FacesMessage("Error",
							"Detalle no actualizado");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			} else {
				// se debe crear un nuevo
				try {
					bodegaDetalle.setBodega(selectedBodega);
					bodegaDetalle.setProducto(selectedProducto);
					bodegaDetalleDAO.create(bodegaDetalle);

					FacesMessage msg = new FacesMessage("Detalle creado");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} catch (Exception e) {
					FacesMessage msg = new FacesMessage("Error",
							"Detalle no creado");
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			}
		} else {
			FacesMessage msg = new FacesMessage("Error",
					"Debe seleccionar la bodega y producto");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}

		return "listarBodegaDetalle";
	}
}
