package pfm.dao;

import pfm.jpa.JPADAOFactory;

public abstract class DAOFactory {
	protected static DAOFactory factory = new JPADAOFactory();

	public static DAOFactory getFactory() {
		return factory;
	}

	public abstract AgenciaDAO getAgenciaDAO();

	public abstract BodegaDAO getBodegaDAO();

	public abstract BodegaDetalleDAO getBodegaDetalleDAO();

	public abstract CategoriaDAO getCategoriaDAO();

	public abstract DescuentoDAO getDescuentoDAO();

	public abstract DescuentoProductoDAO getDescuentoProductoDAO();

	public abstract EmpresaDAO getEmpresaDAO();

	public abstract FacturaDAO getFacturaDAO();

	public abstract FacturaDetalleDAO getFacturaDetalleDAO();

	public abstract MarcaDAO getMarcaDAO();

	public abstract MedioPagoDAO getMedioPagoDAO();

	public abstract ProductoDAO getProductoDAO();

	public abstract RolDAO getRolDAO();

	public abstract UsuarioDAO getUsuarioDAO();

}
