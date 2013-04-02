package pfm.jpa;

import pfm.dao.EmpresaDAO;
import pfm.entidades.Empresa;

public class JPAEmpresaDAO extends JPAGenericDAO<Empresa, Integer> implements
		EmpresaDAO {

	public JPAEmpresaDAO() {
		super(Empresa.class);
	}

}
