package pfm.jpa;

import pfm.dao.CategoriaDAO;
import pfm.entidades.Categoria;

public class JPACategoriaDAO extends JPAGenericDAO<Categoria, Integer> implements CategoriaDAO {

	public JPACategoriaDAO() {
		super(Categoria.class);
	}

}
