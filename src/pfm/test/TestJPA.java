package pfm.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import pfm.entidades.Empresa;
import pfm.entidades.Producto;
import pfm.entidades.Usuario;
import pfm.jpa.JPADAOFactory;

public class TestJPA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Prueba de autenticacion de Usuario
		/*
		 * List<Usuario> usuarios =
		 * JPADAOFactory.getFactory().getUsuarioDAO().find(new
		 * String[]{"username", "password"}, new String[]{"admin","admin"},
		 * null, 0, 0); for (Usuario usuario : usuarios) {
		 * System.out.println(usuario.getApellidos()); }
		 */
		//Prueba de actualizacion de Producto
		try {/*
			Producto pr = JPADAOFactory.getFactory().getProductoDAO().read(1);
			System.out.println(pr.toString());
			pr.setCategoria(JPADAOFactory.getFactory().getCategoriaDAO().read(2));
			System.out.println(pr.toString());
			JPADAOFactory.getFactory().getProductoDAO().update(pr);*/
			EntityManager em = Persistence.createEntityManagerFactory("jpa").createEntityManager();
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
