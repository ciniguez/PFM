package pfm.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Pedido
 * 
 */
@Entity
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fecha;
	@Column(nullable = false)
	private boolean eliminado;
	@Column(nullable = false)
	private double subtotal;
	@Column(nullable = false)
	private double iva;
	@Column(nullable = false)
	private double descuento;
	@Column(nullable = false)
	private double total;
	@Column(nullable = false)
	private boolean pagado;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario cliente;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Usuario empleado;
	@OneToOne
	@JoinColumn(nullable = false)
	private MedioDePago medioDePago;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Bodega bodega;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
	private Set<FacturaDetalle> facturaDetalle;

	private static final long serialVersionUID = 1L;

	public Factura() {
	}

	public Factura(int id, Date fecha, boolean eliminado, double subtotal,
			double iva, double descuento, double total, boolean pagado) {
		this.id = id;
		this.fecha = fecha;
		this.eliminado = eliminado;
		this.subtotal = subtotal;
		this.iva = iva;
		this.descuento = descuento;
		this.total = total;
		this.pagado = pagado;
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

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
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

	public Set<FacturaDetalle> getFacturaDetalle() {
		return facturaDetalle;
	}

	public void setFacturaDetalle(Set<FacturaDetalle> facturaDetalle) {
		this.facturaDetalle = facturaDetalle;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}

	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", eliminado="
				+ eliminado + ", subtotal=" + subtotal + ", iva=" + iva
				+ ", descuento=" + descuento + ", total=" + total + ", pagado="
				+ pagado + ", cliente=" + cliente + ", empleado=" + empleado
				+ ", facturaDetalle=" + facturaDetalle + ", medioDePago="
				+ medioDePago + ", bodega=" + bodega + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bodega == null) ? 0 : bodega.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		long temp;
		temp = Double.doubleToLongBits(descuento);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (eliminado ? 1231 : 1237);
		result = prime * result
				+ ((empleado == null) ? 0 : empleado.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + id;
		temp = Double.doubleToLongBits(iva);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((medioDePago == null) ? 0 : medioDePago.hashCode());
		result = prime * result + (pagado ? 1231 : 1237);
		temp = Double.doubleToLongBits(subtotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(total);
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
		Factura other = (Factura) obj;
		if (bodega == null) {
			if (other.bodega != null)
				return false;
		} else if (!bodega.equals(other.bodega))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (Double.doubleToLongBits(descuento) != Double
				.doubleToLongBits(other.descuento))
			return false;
		if (eliminado != other.eliminado)
			return false;
		if (empleado == null) {
			if (other.empleado != null)
				return false;
		} else if (!empleado.equals(other.empleado))
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
		if (medioDePago == null) {
			if (other.medioDePago != null)
				return false;
		} else if (!medioDePago.equals(other.medioDePago))
			return false;
		if (pagado != other.pagado)
			return false;
		if (Double.doubleToLongBits(subtotal) != Double
				.doubleToLongBits(other.subtotal))
			return false;
		if (Double.doubleToLongBits(total) != Double
				.doubleToLongBits(other.total))
			return false;
		return true;
	}

}
