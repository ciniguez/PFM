package pfm.dao;

import pfm.jpa.JPADAOFactory;



public abstract class DAOFactory {
	protected static DAOFactory factory = new JPADAOFactory();

	public static DAOFactory getFactory() {
		return factory;
	}

	public abstract AgenciaDAO getJPAgenciaDAO();
	public abstract BodegaDAO getJPABodegaDAO();
	public abstract BodegaDetalleDAO getJPABodegaDetalleDAO();
	public abstract CategoriaDAO getJPACategoriaDAO();
	public abstract DescuentoDAO getJPADescuentoDAO();
	public abstract DescuentoProductoDAO getJPADescuentoProductoDAO();
	public abstract EmpresaDAO getJPAEmpresaDAO();
	public abstract FacturaDAO getJPAFacturaDAO();
	public abstract FacturaDetalleDAO getJPAFacturaDetalleDAO();
	public abstract MarcaDAO getJPAMarcaDAO();
	public abstract MedioPagoDAO getJPAMedioPagoDAO();
	public abstract ProductoDAO getJPAProductoDAO();
	public abstract RolDAO getJPARolDAO();
	public abstract UsuarioDAO getJPAUsuarioDAO();

}
