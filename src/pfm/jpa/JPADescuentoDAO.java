package pfm.jpa;

import java.util.Date;

import javax.persistence.Query;

import pfm.dao.DescuentoDAO;
import pfm.entidades.Descuento;

public class JPADescuentoDAO extends JPAGenericDAO<Descuento, Integer>
		implements DescuentoDAO {

	public JPADescuentoDAO() {
		super(Descuento.class);
	}

	@Override
	public Descuento getValorDescuentoByFecha(int id, boolean eliminado) {
		try {
			Date fecha = new Date();
			Query query = em.createNamedQuery("getValorDescuentoByFecha");
			query.setParameter("id", id);
			query.setParameter("eliminado", eliminado);
			query.setParameter("fechaActual", fecha);
			Descuento resultado = (Descuento) query.getSingleResult();
			return resultado;
		} catch (Exception e) {
			//System.out.println("ERROR: getValorDescuentoByFecha " + e);
			return null;
		}

	}
}
