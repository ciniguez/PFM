package pfm.entidades;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

/**
 * Entity implementation class for Entity: DescuentoProducto
 * 
 */
@Entity
@Table(name = "DESCUENTO_PRODUCTO")
@Indexes({
		@Index(name = "FK_DESCUENTO_PRODUCTO_BODEGADETALLE_ID", columnNames = { "BODEGADETALLE_ID" }),
		@Index(name = "FK_DESCUENTO_PRODUCTO_DESCUENTO_ID", columnNames = { "DESCUENTO_ID" }),
		@Index(name = "UK_DESCUENTO_PRODUCTO", columnNames = {
				"BODEGADETALLE_ID", "DESCUENTO_ID" }, unique = true) })
public class DescuentoProducto implements Serializable {

	@Id
	private int id;
	@Column(nullable = false)
	private boolean eliminado;
	@OneToOne
	@JoinColumn(nullable = false)
	private BodegaDetalle bodegaDetalle;
	@OneToOne
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

	public BodegaDetalle getBodegaDetalle() {
		return bodegaDetalle;
	}

	public void setBodegaDetalle(BodegaDetalle bodegaDetalle) {
		this.bodegaDetalle = bodegaDetalle;
	}

	public Descuento getDescuento() {
		return descuento;
	}

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	@Override
	public String toString() {
		return "DescuentoProducto [id=" + id + ", eliminado=" + eliminado
				+ ", bodegaDetalle=" + bodegaDetalle + ", descuento="
				+ descuento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bodegaDetalle == null) ? 0 : bodegaDetalle.hashCode());
		result = prime * result
				+ ((descuento == null) ? 0 : descuento.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + id;
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
		if (bodegaDetalle == null) {
			if (other.bodegaDetalle != null)
				return false;
		} else if (!bodegaDetalle.equals(other.bodegaDetalle))
			return false;
		if (descuento == null) {
			if (other.descuento != null)
				return false;
		} else if (!descuento.equals(other.descuento))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}
