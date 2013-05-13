package pfm.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class TestDB {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		EntityManager em = Persistence.createEntityManagerFactory("jpa")
				.createEntityManager();

	}
}
