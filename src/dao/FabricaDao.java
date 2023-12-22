package dao;

import dao_imp.DepartamentoDaoJDBC;
import dao_imp.VendedorDaoJDBC;
import db.DB;

public class FabricaDao {

	public static VendedorDao criarVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
	
	public static DepartamentoDao criarDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
}
