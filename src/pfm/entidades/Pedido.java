package pfm.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Pedido
 * 
 */
@Entity
public class Pedido implements Serializable {

	@Id
	private int id;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private boolean eliminado;
	private double subtotal;
	private double iva;
	@ManyToOne
	@JoinColumn
	private Usuario cliente;
	@ManyToOne
	@JoinColumn
	private Usuario empleado;
	private static final long serialVersionUID = 1L;

	public Pedido() {
	}

	public Pedido(int id, Date fecha, boolean eliminado, double subtotal,
			double iva) {
		this.id = id;
		this.fecha = fecha;
		this.eliminado = eliminado;
		this.subtotal = subtotal;
		this.iva = iva;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean getEliminado() {
		return this.eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getIva() {
		return this.iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", fecha=" + fecha + ", eliminado="
				+ eliminado + ", subtotal=" + subtotal + ", iva=" + iva
				+ ", cliente=" + cliente + ", empleado=" + empleado + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(iva);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(subtotal);
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
		Pedido other = (Pedido) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(iva) != Double.doubleToLongBits(other.iva))
			return false;
		if (Double.doubleToLongBits(subtotal) != Double
				.doubleToLongBits(other.subtotal))
			return false;
		return true;
	}

}
