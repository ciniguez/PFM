package pfm.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class TestDB {
	public static void main(String[] args) {
		EntityManager em = Persistence.createEntityManagerFactory("jpa")
				.createEntityManager();

	}
}
