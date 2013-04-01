package pfm.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class TestJPA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = Persistence.createEntityManagerFactory("jpa").createEntityManager();
	}

}
