package pfm.beans.menu;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "menu")
@RequestScoped
public class Menu {
	
	public String listarEmpresa(){return "empresa";}
	public String listarAgencia(){return "agencia";}
	public String listarBodega(){return "bodega";}
	
	public String listarMarca(){ return "marca";}
	public String listarProducto(){ return "producto";}
	public String listarCategoria(){ return "categoria";}
	public String listarBodegaDetalle(){ return "bodegaDetalle";}
	
	public String listarDescuento(){ return "descuento";}
	public String listarDescuentoProducto(){ return "descuentoProducto";}
	
	public String listarMedioPago(){ return "medioPago";}
	public String listarFactura(){ return "factura";}
	public String listarFacturaPendiente(){ return "facturaPendiente";}
	
	public String listarRol(){ return "rol";}
	public String listarUsuario(){ return "usuario";}
	public String listarEmpleadoAgencia(){ return "empleadoAgencia";}
	
	public String listarReportes(){ return "reportes";}

}
