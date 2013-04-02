package pfm.entidades;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Descuento
 * 
 */
@Entity
public class Descuento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(unique = true, nullable = false)
	private String nombre;
	@Column(nullable = false)
	private double valor;
	@Column(nullable = false)
	private boolean eliminado;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "descuento")
	private DescuentoProducto descuentoProducto;
	private static final long serialVersionUID = 1L;

	public Descuento() {

	}

	public Descuento(int id, String nombre, double valor, boolean eliminado) {
		this.id = id;
		this.nombre = nombre;
		this.valor = valor;
		this.eliminado = eliminado;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public DescuentoProducto getDescuentoProducto() {
		return descuentoProducto;
	}

	public void setDescuentoProducto(DescuentoProducto descuentoProducto) {
		this.descuentoProducto = descuentoProducto;
	}

	@Override
	public String toString() {
		return "Descuento [id=" + id + ", nombre=" + nombre + ", valor="
				+ valor + ", eliminado=" + eliminado + ", descuentoProducto="
				+ descuentoProducto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((descuentoProducto == null) ? 0 : descuentoProducto
						.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Descuento other = (Descuento) obj;
		if (descuentoProducto == null) {
			if (other.descuentoProducto != null)
				return false;
		} else if (!descuentoProducto.equals(other.descuentoProducto))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Double.doubleToLongBits(valor) != Double
				.doubleToLongBits(other.valor))
			return false;
		return true;
	}

}
