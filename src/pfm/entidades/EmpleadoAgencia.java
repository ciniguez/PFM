package pfm.entidades;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

/**
 * Entity implementation class for Entity: EmpleadoAgencia
 * 
 */
@XmlRootElement
@NamedQuery(name = "getAgenciaByEmpleado", query = "SELECT a FROM EmpleadoAgencia a WHERE a.empleado = :empleado")
@Entity
@Table(name = "EMPLEADO_AGENCIA")
@Indexes({
		@Index(name = "FK_EMPLEADO_AGENCIA_AGENCIA_ID", columnNames = { "AGENCIA_ID" }),
		@Index(name = "FK_EMPLEADO_AGENCIA_EMPLEADO_ID", columnNames = { "EMPLEADO_ID" }),
		@Index(name = "UK_EMPLEADO_AGENCIA", columnNames = { "AGENCIA_ID",
				"EMPLEADO_ID" }, unique = true) })
public class EmpleadoAgencia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(nullable = false)
	private boolean eliminado;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario empleado;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Agencia agencia;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "empleadoAgencia")
	private Set<Factura> factura;

	private static final long serialVersionUID = 1L;

	public EmpleadoAgencia() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getEliminado() {
		return this.eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	@XmlTransient
	public Set<Factura> getFactura() {
		return factura;
	}

	public void setFactura(Set<Factura> factura) {
		this.factura = factura;
	}

	@Override
	public String toString() {
		return "EmpleadoAgencia [id=" + id + ", eliminado=" + eliminado
				+ ", empleado=" + empleado + ", agencia=" + agencia + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result
				+ ((empleado == null) ? 0 : empleado.hashCode());
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
		EmpleadoAgencia other = (EmpleadoAgencia) obj;
		if (agencia == null) {
			if (other.agencia != null)
				return false;
		} else if (!agencia.equals(other.agencia))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}
