package pfm.entidades;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

/**
 * Entity implementation class for Entity: BodegaDetalle
 * 
 */
@NamedQuery(name = "getBodegaDetalleByBodegaAndProducto", query = "SELECT d FROM BodegaDetalle d WHERE d.producto = :producto AND d.bodega = :bodega")
@Entity
@Table(name = "BODEGA_DETALLE")
@Indexes({
		@Index(name = "FK_BODEGA_DETALLE_PRODUCTO_ID", columnNames = { "PRODUCTO_ID" }),
		@Index(name = "FK_BODEGA_DETALLE_BODEGA_ID", columnNames = { "BODEGA_ID" }),
		@Index(name = "UK_BODEGA_DETALLE", columnNames = { "BODEGA_ID",
				"PRODUCTO_ID" }, unique = true) })
public class BodegaDetalle implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(nullable = false)
	private int cantidad;
	@Column(nullable = false)
	private double precio;
	@Column(nullable = false)
	private boolean eliminado;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Bodega bodega;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bodegaDetalle")
	private Set<FacturaDetalle> facturaDetalle;

	private static final long serialVersionUID = 1L;

	public BodegaDetalle() {

	}

	public BodegaDetalle(int id, int cantidad, double precio, boolean eliminado) {
		this.id = id;
		this.cantidad = cantidad;
		this.precio = precio;
		this.eliminado = eliminado;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return this.precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Set<FacturaDetalle> getFacturaDetalle() {
		return facturaDetalle;
	}

	public void setFacturaDetalle(Set<FacturaDetalle> facturaDetalle) {
		this.facturaDetalle = facturaDetalle;
	}

	@Override
	public String toString() {
		return "BodegaDetalle [id=" + id + ", cantidad=" + cantidad
				+ ", precio=" + precio + ", eliminado=" + eliminado
				+ ", bodega=" + bodega + ", producto=" + producto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bodega == null) ? 0 : bodega.hashCode());
		result = prime * result + cantidad;
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(precio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((producto == null) ? 0 : producto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BodegaDetalle other = (BodegaDetalle) obj;
		if (bodega == null) {
			if (other.bodega != null)
				return false;
		} else if (!bodega.equals(other.bodega))
			return false;
		if (cantidad != other.cantidad)
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(precio) != Double
				.doubleToLongBits(other.precio))
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		return true;
	}

}
