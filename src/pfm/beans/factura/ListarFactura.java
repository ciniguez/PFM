package pfm.beans.factura;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import pfm.dao.FacturaDAO;
import pfm.entidades.Factura;

@ManagedBean(name = "listarFactura")
public class ListarFactura implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{DAOFactory.facturaDAO}")
	private FacturaDAO facturaDAO;
	private List<Factura> lista;
	private List<Factura> filtered;
	private Factura[] selectedFacturas;

	public ListarFactura() {

	}

	public FacturaDAO getFacturaDAO() {
		return facturaDAO;
	}

	public void setFacturaDAO(FacturaDAO facturaDAO) {
		this.facturaDAO = facturaDAO;
	}

	public List<Factura> getLista() {
		String[] attributes = {};
		String[] values = {};
		String order = "id";
		int index = -1;
		int size = -1;
		setLista(facturaDAO.find(attributes, values, order, index, size));
		return lista;
	}

	public void setLista(List<Factura> lista) {
		this.lista = lista;
	}

	public List<Factura> getFiltered() {
		return filtered;
	}

	public void setFiltered(List<Factura> filtered) {
		this.filtered = filtered;
	}

	public Factura[] getSelectedFacturas() {
		return selectedFacturas;
	}

	public void setSelectedFacturas(Factura[] selectedFacturas) {
		this.selectedFacturas = selectedFacturas;
	}

}
