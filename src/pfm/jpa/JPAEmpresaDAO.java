package pfm.jpa;

import javax.persistence.Query;

import pfm.dao.EmpresaDAO;
import pfm.entidades.Empresa;

public class JPAEmpresaDAO extends JPAGenericDAO<Empresa, Integer> implements
		EmpresaDAO {

	public JPAEmpresaDAO() {
		super(Empresa.class);
	}

	@Override
	public double getIvaByEmpresa(int id, boolean eliminado) {
		try {
			Query query = em.createNamedQuery("getIvaByEmpresa");
			query.setParameter("id", id);
			query.setParameter("eliminado", eliminado);
			Empresa resultado = (Empresa) query.getSingleResult();
			return resultado.getIva();
		} catch (Exception e) {
			System.out.println("ERROR: getIvaByEmpresa " + e);
			return 0;

		}
	}

}
