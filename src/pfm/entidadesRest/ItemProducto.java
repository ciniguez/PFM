package pfm.entidadesRest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemProducto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idBodegaDetalle;
	private int idFacturaDetalle;
	private String nombreProducto;
	private double subtotal;
	private int cantidad;
	private double precio;
	
	public ItemProducto(){
		
	}
	
	public ItemProducto(int idBodegaDetalle, String nombreProducto, double subtotal, int cantidad, double precio, int idFacturaDetalle) {
		this.idBodegaDetalle = idBodegaDetalle;
		this.nombreProducto = nombreProducto;
		this.subtotal = subtotal;
		this.cantidad = cantidad;
		this.precio = precio;
		this.idFacturaDetalle = idFacturaDetalle;
	}
	
	public int getIdBodegaDetalle() {
		return idBodegaDetalle;
	}
	public void setIdBodegaDetalle(int idBodegaDetalle) {
		this.idBodegaDetalle = idBodegaDetalle;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getIdFacturaDetalle() {
		return idFacturaDetalle;
	}

	public void setIdFacturaDetalle(int idFacturaDetalle) {
		this.idFacturaDetalle = idFacturaDetalle;
	}

}
