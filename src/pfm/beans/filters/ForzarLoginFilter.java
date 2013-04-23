package pfm.beans.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import pfm.entidades.Usuario;

/**
 * TITULO: Implementación de un filtro para Chequear el estado de Login del usuario.
 * OBJETIVO: 
 * Hacer que la aplicación no pueda ser accedida a ninguna pagina sin antes logearse, y 
 * si alguien intenta acceder a la pagina esta lo redireccionara a la pagina de login. 
 * En el login el usuario podrá logearse y así poder realizar sus tareas.
 * IMPLEMENTACION:
 * Para cumplir con el objetivo mencionado, se aplica el concepto de "servlet filter", 
 * que permite operar sobre una request antes que esta sea procesada. Esto nos permite 
 * chequear si el usuario esta logueado cuando quiere acceder a alguna pagina. 
 * TRAZABILIDAD:
 * Esta clase tiene relación con los siguientes archivos:
 * - web.xml .- donde se declara la presencia del filtro.
 * - pfm.beans.login.LoginUsuario.java  .- donde se crea la sesion "UsuarioBean" y 
 * se almacena el usuario.
 * @author Carlos Iniguez (2013)
 *
 */

public class ForzarLoginFilter implements Filter {

	private static final String LOGIN_JSP = "/login/loginUsuario.xhtml";
	
	public ForzarLoginFilter() {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * Realiza la revisión de que un Usuario exista en sesión y que esté logeado.
	 * @author Carlos Iniguez (2013)
	 * @param request
	 * @param response
	 * @return TRUE si existe un usuario en Sesión y este está logeado.
	 * @throws IOException
	 * @throws ServletException
	 */
	private static boolean checkLoginState(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		boolean esLogeado = false;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		Usuario managedUsuarioBean = null;
		// Si existe un Usuario en Sesion, y tiene la propiedad isLogeado seteada en true.  
		if (null != session && (null != (managedUsuarioBean = (Usuario) session.getAttribute("UsuarioBean")))) {
			if (managedUsuarioBean.isLogeado()) {
				esLogeado = true;
			}
		}
		return esLogeado;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		boolean isLoggedIn = checkLoginState(request, response);
		if (isRedirect((HttpServletRequest) request) && !isLoggedIn) {
			String loginURI = LOGIN_JSP;
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(loginURI);
			// Force the login
			requestDispatcher.forward(request, response);
			return;

		} else {
			try {
				chain.doFilter(request, response);
			} catch (Throwable t) {
				// A production quality implementation will
				// deal with this exception.
			}
		}
	}

	private boolean isRedirect(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return (!requestURI.contains(LOGIN_JSP));
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
