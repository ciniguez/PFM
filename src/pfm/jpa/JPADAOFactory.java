package pfm.jpa;

import pfm.dao.AgenciaDAO;
import pfm.dao.BodegaDAO;
import pfm.dao.BodegaDetalleDAO;
import pfm.dao.CategoriaDAO;
import pfm.dao.DAOFactory;
import pfm.dao.DescuentoDAO;
import pfm.dao.DescuentoProductoDAO;
import pfm.dao.EmpresaDAO;
import pfm.dao.FacturaDAO;
import pfm.dao.FacturaDetalleDAO;
import pfm.dao.MarcaDAO;
import pfm.dao.MedioPagoDAO;
import pfm.dao.ProductoDAO;
import pfm.dao.RolDAO;
import pfm.dao.UsuarioDAO;


public class JPADAOFactory extends DAOFactory {

	@Override
	public AgenciaDAO getJPAgenciaDAO(){
		return new JPAAgenciaDAO();
	}
	@Override
	public BodegaDAO getJPABodegaDAO(){
		return new JPABodegaDAO();
	}
	@Override
	public BodegaDetalleDAO getJPABodegaDetalleDAO(){
		return new JPABodegaDetalleDAO();
	}
	@Override
	public CategoriaDAO getJPACategoriaDAO(){
		return new JPACategoriaDAO();
	}
	@Override
	public DescuentoDAO getJPADescuentoDAO(){
		return new JPADescuentoDAO();
	}
	@Override
	public DescuentoProductoDAO getJPADescuentoProductoDAO(){
		return new JPADescuentoProductoDAO();
	}
	@Override
	public EmpresaDAO getJPAEmpresaDAO(){
		return new JPAEmpresaDAO();
	}
	@Override
	public FacturaDAO getJPAFacturaDAO(){
		return new JPAFacturaDAO();
	}
	@Override
	public FacturaDetalleDAO getJPAFacturaDetalleDAO(){
		return new JPAFacturaDetalleDAO();
	}
	@Override
	public MarcaDAO getJPAMarcaDAO(){
		return new JPAMarcaDAO();
	}
	@Override
	public MedioPagoDAO getJPAMedioPagoDAO(){
		return new JPAMedioPagoDAO();
	}
	@Override
	public ProductoDAO getJPAProductoDAO(){
		return new JPAProductoDAO();
	}
	@Override
	public RolDAO getJPARolDAO(){
		return new JPARolDAO();
	}
	@Override
	public UsuarioDAO getJPAUsuarioDAO(){
		return new JPAUsuarioDAO();
	}
	
}
