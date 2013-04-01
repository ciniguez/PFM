package pfm.dao;


public abstract class DAOFactory {
	protected static DAOFactory factory;

	public static DAOFactory getFactory() {
		return factory;
	}


}
