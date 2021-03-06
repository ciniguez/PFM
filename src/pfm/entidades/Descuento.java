package pfm.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity implementation class for Entity: Descuento
 * 
 */
@XmlRootElement
@NamedQuery(name = "getValorDescuentoByFecha", query = "SELECT d FROM Descuento d WHERE d.id = :id AND d.eliminado = :eliminado AND :fechaActual BETWEEN d.fechaInicio AND d.fechaFin ")
@Entity
public class Descuento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(unique = true, nullable = false)
	private String nombre;
	@Column(nullable = false)
	private double valor;
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO", nullable = false)
	private Date fechaInicio;
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN", nullable = false)
	private Date fechaFin;
	@Column(nullable = false)
	private boolean eliminado;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "descuento")
	private Set<DescuentoProducto> descuentoProducto;
	private static final long serialVersionUID = 1L;

	public Descuento() {

	}

	public Descuento(int id, String nombre, double valor, Date fechaInicio,
			Date fechaFin, boolean eliminado) {
		this.id = id;
		this.nombre = nombre;
		this.valor = valor;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	@XmlTransient
	public Set<DescuentoProducto> getDescuentoProducto() {
		return descuentoProducto;
	}

	public void setDescuentoProducto(Set<DescuentoProducto> descuentoProducto) {
		this.descuentoProducto = descuentoProducto;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result
				+ ((fechaFin == null) ? 0 : fechaFin.hashCode());
		result = prime * result
				+ ((fechaInicio == null) ? 0 : fechaInicio.hashCode());
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
		if (eliminado != other.eliminado)
			return false;
		if (fechaFin == null) {
			if (other.fechaFin != null)
				return false;
		} else if (!fechaFin.equals(other.fechaFin))
			return false;
		if (fechaInicio == null) {
			if (other.fechaInicio != null)
				return false;
		} else if (!fechaInicio.equals(other.fechaInicio))
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
