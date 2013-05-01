package pfm.entidadesRest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CarroCompras implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idFactura;
	private String nombreAgencia;
	private String fechaCreacion;
	private double total;

	public CarroCompras() {

	}

	public CarroCompras(int idFactura, String nombreAgencia, String fechaCreacion, double total) {
		super();
		this.idFactura = idFactura;
		this.nombreAgencia = nombreAgencia;
		this.fechaCreacion = fechaCreacion;
		this.total = total;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
