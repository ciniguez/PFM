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

		try {
			Empresa empresa = new Empresa();
			empresa.setTelefono("");
			empresa.setDireccion("Ninguna");
			empresa.setEliminado(false);
			empresa.setRazonSocial("Empresa XXX-5");
			empresa.setRuc("1111111111111");
			JPADAOFactory.getFactory().getEmpresaDAO().create(empresa);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}
}
