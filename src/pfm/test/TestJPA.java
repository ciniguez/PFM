package pfm.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import pfm.entidades.Empresa;
import pfm.jpa.JPADAOFactory;

public class TestJPA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Prueba de autenticacion de Usuario
		/*
		 * List<Usuario> usuarios =
		 * JPADAOFactory.getFactory().getUsuarioDAO().find(new
		 * String[]{"username", "password"}, new String[]{"admin","admin"},
		 * null, 0, 0); for (Usuario usuario : usuarios) {
		 * System.out.println(usuario.getApellidos()); }
		 */
		// Prueba de actualizacion de Producto
		/*
		 * try { Producto pr =
		 * JPADAOFactory.getFactory().getProductoDAO().read(1);
		 * System.out.println(pr.toString());
		 * pr.setCategoria(JPADAOFactory.getFactory
		 * ().getCategoriaDAO().read(2)); System.out.println(pr.toString());
		 * JPADAOFactory.getFactory().getProductoDAO().update(pr); } catch
		 * (Exception ex) { System.out.println(ex); }
		 */

		// Prueba de actualizacion de empresa
		try {
			// Empresa em = JPADAOFactory.getFactory().getEmpresaDAO().read(1);
			// System.out.println(em.toString());
			// em.setIva(19.20);
			// System.out.println(em.toString());
			// JPADAOFactory.getFactory().getEmpresaDAO().update(em);
			EntityManager em = Persistence.createEntityManagerFactory("jpa")
					.createEntityManager();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		/*
		 * try { Empresa empresa = new Empresa(); empresa.setTelefono("");
		 * empresa.setDireccion("Ninguna"); empresa.setEliminado(false);
		 * empresa.setRazonSocial("Empresa XXX-5");
		 * empresa.setRuc("1111111111111");
		 * JPADAOFactory.getFactory().getEmpresaDAO().create(empresa); } catch
		 * (Exception ex) { System.out.println(ex.getMessage()); }
		 */
	}
}
