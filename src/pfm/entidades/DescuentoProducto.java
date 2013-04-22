package pfm.entidades;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

/**
 * Entity implementation class for Entity: DescuentoProducto
 * 
 */
@NamedQuery(name = "getDescuentoId", query = "SELECT d FROM DescuentoProducto d WHERE d.producto = :producto AND d.eliminado = :eliminado")
@Entity
@Table(name = "DESCUENTO_PRODUCTO")
@Indexes({
		@Index(name = "FK_DESCUENTO_PRODUCTO_PRODUCTO_ID", columnNames = { "PRODUCTO_ID" }),
		@Index(name = "FK_DESCUENTO_PRODUCTO_DESCUENTO_ID", columnNames = { "DESCUENTO_ID" }),
		@Index(name = "UK_DESCUENTO_PRODUCTO", columnNames = { "PRODUCTO_ID",
				"DESCUENTO_ID" }, unique = true) })
public class DescuentoProducto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(nullable = false)
	private boolean eliminado;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Descuento descuento;
	private static final long serialVersionUID = 1L;

	public DescuentoProducto() {
	}

	public DescuentoProducto(int id, boolean eliminado) {
		this.id = id;
		this.eliminado = eliminado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Descuento getDescuento() {
		return descuento;
	}

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}

	@Override
	public String toString() {
		return "DescuentoProducto [id=" + id + ", eliminado=" + eliminado
				+ ", producto=" + producto + ", descuento=" + descuento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descuento == null) ? 0 : descuento.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + id;
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
		DescuentoProducto other = (DescuentoProducto) obj;
		if (descuento == null) {
			if (other.descuento != null)
				return false;
		} else if (!descuento.equals(other.descuento))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (id != other.id)
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		return true;
	}

}
