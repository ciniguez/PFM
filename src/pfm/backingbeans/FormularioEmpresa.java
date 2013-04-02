package pfm.backingbeans;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pfm.entidades.Empresa;

@ManagedBean(name = "formularioEmpresa")
@SessionScoped
public class FormularioEmpresa {


	private Empresa empresa = new Empresa();
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	private ResourceBundle misRecursos = ResourceBundle.getBundle("i18n.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

	
	/**
	 *  GETTERS AND SETTERS
	 * 
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * EVENTOS PERSONALES
	 */
	/**
	 * Procesa el Formulario (validando que las dos direcciones de mail ingresadas sean iguales)
	 */
	public String procesar() {
		return "";

	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getMisRecursos() {
		return misRecursos;
	}

	public void setMisRecursos(ResourceBundle misRecursos) {
		this.misRecursos = misRecursos;
	}

}
