package pfm.dao;

import pfm.entidades.Empresa;

public interface EmpresaDAO extends GenericDAO<Empresa, Integer> {

	public double getIvaByEmpresa(int id, boolean eliminado);

}
