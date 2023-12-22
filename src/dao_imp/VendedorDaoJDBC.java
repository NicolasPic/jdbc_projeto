package dao_imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.VendedorDao;
import db.DB;
import db.DbException;
import entidades.Departamento;
import entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao 
{

	private Connection conn;

	public VendedorDaoJDBC(Connection conn) 
	{
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor obj) 
{
		
		PreparedStatement st = null;
		try 
		{
			st = conn.prepareStatement("INSERT INTO seller\r\n"
					+ "		(Name, Email, BirthDate, BaseSalary, DepartmentId)\r\n"
					+ "		VALUES\r\n"
					+ "		(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1,obj.getNome());
			st.setString(2,obj.getEmail());
			st.setDate(3, new  java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			
			int rws = st.executeUpdate();
			
			if(rws > 0) 
			{
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) 
				{
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResult(rs);
			}
			else 
			{
				throw new DbException("Error não esperado");
			}
		}
		catch(SQLException e) 
		{
			throw new DbException(e.getMessage());
		}
		finally 
		{
			DB.closeStatement(st);
		}
	
	}

	@Override
	public void update(Vendedor obj) 
	{
		PreparedStatement st = null;
		try 
		{
			st = conn.prepareStatement("UPDATE seller\r\n"
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\r\n"
					+ "WHERE Id = ?");
			
			st.setString(1,obj.getNome());
			st.setString(2,obj.getEmail());
			st.setDate(3, new  java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			st.executeUpdate();
	

		}
		catch(SQLException e) 
		{
			throw new DbException(e.getMessage());
		}
		finally 
		{
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletePorId(Integer id) 
    {
		PreparedStatement st = null;
		try 
		{
			st = conn.prepareStatement("DELETE FROM seller\r\n"
					+ "WHERE Id = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) 
		{
			throw new DbException(e.getMessage());
		}
		 finally 
		 {
				DB.closeStatement(st);
		 }
	}

	@Override
	public Vendedor achePorId(Integer id) 
	{
		PreparedStatement st = null;
		ResultSet rs = null;

		try 
		{
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName\r\n" + "FROM seller INNER JOIN department\r\n"
							+ "ON seller.DepartmentId = department.Id\r\n" + "WHERE seller.Id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Departamento dep = instanciarDepartamento(rs);
				Vendedor obj = instanciarVendedor(rs, dep);
				return obj;
			}
			return null;

		}
		catch (SQLException e)
        {
			throw new DbException(e.getMessage());
		} 
		finally 
		{
			DB.closeStatement(st);
			DB.closeResult(rs);
		}

	}

	private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException 
	{
		Vendedor obj = new Vendedor();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setSalarioBase(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartamento(dep);
		return obj;
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException 
	{
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Vendedor> econtreTodos() 
	{

			PreparedStatement st = null;
			ResultSet rs = null;

			try 
			{
				st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n"
						+ "FROM seller INNER JOIN department\r\n" + "ON seller.DepartmentId = department.Id\r\n"
						+ "ORDER BY Name");


				rs = st.executeQuery();

				List<Vendedor> list = new ArrayList<>();
				Map<Integer, Departamento> map = new HashMap<>();

				while (rs.next()) 
				{

					Departamento dep = map.get(rs.getInt("DepartmentId"));

					if (dep == null) 
					{
						dep = instanciarDepartamento(rs);
						map.put(rs.getInt("DepartmentId"), dep);
					}
					Vendedor obj = instanciarVendedor(rs, dep);
					list.add(obj);
				}
				return list;

			} 
			catch (SQLException e) 
			{
				throw new DbException(e.getMessage());
			} 
			finally 
			{
				DB.closeStatement(st);
				DB.closeResult(rs);
			}
	}

	@Override
	public List<Vendedor> achePorDep(Departamento departament) 
	{
		PreparedStatement st = null;
		ResultSet rs = null;

		try 
		{
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n"
					+ "FROM seller INNER JOIN department\r\n" + "ON seller.DepartmentId = department.Id\r\n"
					+ "WHERE DepartmentId = ?\r\n" + "ORDER BY Name");

			st.setInt(1, departament.getId());

			rs = st.executeQuery();

			List<Vendedor> list = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();

			while (rs.next()) 
			{

				Departamento dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) 
				{
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Vendedor obj = instanciarVendedor(rs, dep);
				list.add(obj);
			}
			return list;

		} 
		catch (SQLException e) 
		{
			throw new DbException(e.getMessage());
		} 
		finally 
		{
			DB.closeStatement(st);
			DB.closeResult(rs);
		}

	}

}
