package pfm.beans.menu;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import pfm.entidades.Usuario;

@ManagedBean(name = "menu")
@RequestScoped
public class Menu {
	
	public boolean getIsAdmin(){
		Usuario usuarioSesion = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UsuarioBean");
		//TODO: Poner en enumeracion los perfiles
		if(usuarioSesion.getRol().getId()==3)
			return true;
		else
			return false;
			
	}
	
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
	public String listarFacturaPendiente(){ return "facturaPendiente";}
	public String listarFacturasPagadas(){return "facturaPagada";}
	
	public String listarRol(){ return "rol";}
	public String listarUsuario(){ return "usuario";}
	public String listarEmpleadoAgencia(){ return "empleadoAgencia";}
	
	public String listarReportes(){ return "reportes";}

}
