package pfm.jpa;

import javax.faces.bean.ManagedBean;

import pfm.dao.AgenciaDAO;
import pfm.dao.BodegaDAO;
import pfm.dao.BodegaDetalleDAO;
import pfm.dao.CategoriaDAO;
import pfm.dao.DAOFactory;
import pfm.dao.DescuentoDAO;
import pfm.dao.DescuentoProductoDAO;
import pfm.dao.EmpleadoAgenciaDAO;
import pfm.dao.EmpresaDAO;
import pfm.dao.FacturaDAO;
import pfm.dao.FacturaDetalleDAO;
import pfm.dao.MarcaDAO;
import pfm.dao.MedioPagoDAO;
import pfm.dao.ProductoDAO;
import pfm.dao.RolDAO;
import pfm.dao.UsuarioDAO;

@ManagedBean(name = "DAOFactory")
public class JPADAOFactory extends DAOFactory {

	@Override
	public AgenciaDAO getAgenciaDAO() {
		return new JPAAgenciaDAO();
	}

	@Override
	public BodegaDAO getBodegaDAO() {
		return new JPABodegaDAO();
	}

	@Override
	public BodegaDetalleDAO getBodegaDetalleDAO() {
		return new JPABodegaDetalleDAO();
	}

	@Override
	public CategoriaDAO getCategoriaDAO() {
		return new JPACategoriaDAO();
	}

	@Override
	public DescuentoDAO getDescuentoDAO() {
		return new JPADescuentoDAO();
	}

	@Override
	public DescuentoProductoDAO getDescuentoProductoDAO() {
		return new JPADescuentoProductoDAO();
	}

	@Override
	public EmpresaDAO getEmpresaDAO() {
		return new JPAEmpresaDAO();
	}

	@Override
	public FacturaDAO getFacturaDAO() {
		return new JPAFacturaDAO();
	}

	@Override
	public FacturaDetalleDAO getFacturaDetalleDAO() {
		return new JPAFacturaDetalleDAO();
	}

	@Override
	public MarcaDAO getMarcaDAO() {
		return new JPAMarcaDAO();
	}

	@Override
	public MedioPagoDAO getMedioPagoDAO() {
		return new JPAMedioPagoDAO();
	}

	@Override
	public ProductoDAO getProductoDAO() {
		return new JPAProductoDAO();
	}

	@Override
	public RolDAO getRolDAO() {
		return new JPARolDAO();
	}

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new JPAUsuarioDAO();
	}

	@Override
	public EmpleadoAgenciaDAO getEmpleadoAgenciaDAO() {		
		return new JPAEmpleadoAgenciaDAO();
	}

}
