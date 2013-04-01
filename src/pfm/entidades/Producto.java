package pfm.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Producto
 * 
 */
@Entity
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String nombre;
	private boolean eliminado;
	@ManyToOne
	@JoinColumn
	private Marca marca;
	@ManyToOne
	@JoinColumn
	private Categoria categoria;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
	private Set<BodegaDetalle> bodegaDetalle;
	private static final long serialVersionUID = 1L;

	public Producto() {

	}

	public Producto(int id, String nombre, boolean eliminado) {
		this.id = id;
		this.nombre = nombre;
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

	public boolean getEliminado() {
		return this.eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Set<BodegaDetalle> getBodegaDetalle() {
		return bodegaDetalle;
	}

	public void setBodegaDetalle(Set<BodegaDetalle> bodegaDetalle) {
		this.bodegaDetalle = bodegaDetalle;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", eliminado="
				+ eliminado + ", marca=" + marca + ", categoria=" + categoria
				+ ", bodegaDetalle=" + bodegaDetalle + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bodegaDetalle == null) ? 0 : bodegaDetalle.hashCode());
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Producto other = (Producto) obj;
		if (bodegaDetalle == null) {
			if (other.bodegaDetalle != null)
				return false;
		} else if (!bodegaDetalle.equals(other.bodegaDetalle))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (id != other.id)
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}
