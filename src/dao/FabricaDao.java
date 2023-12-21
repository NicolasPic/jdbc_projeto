package dao;

import dao_imp.VendedorDaoJDBC;
import db.DB;

public class FabricaDao {

	public static VendedorDao criarVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
	
}
