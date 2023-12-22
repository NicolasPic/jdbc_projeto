package dao_imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DepartamentoDao;
import db.DB;
import db.DbException;
import db.DbIntegrityException;
import entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao{
	
	private Connection conn;

	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Departamento obj) 
{
		PreparedStatement st = null;
		try 
		{
			st =conn.prepareStatement("INSERT INTO Department\r\n"
					+ "		(Name)\r\n"
					+ "		VALUES\r\n"
					+ "		(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1,obj.getNome());
			
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
	public void update(Departamento obj) 
	{
		PreparedStatement st = null;
		try
		{
			st = conn.prepareStatement(
					"UPDATE department " +
					"SET Name = ? " +
					"WHERE Id = ?");
		
		st.setString(1, obj.getNome());
		st.setInt(2, obj.getId());
		
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
	public void deletePorId(Integer id) {
		PreparedStatement st = null;
		
		try
		{
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Departamento achePorId(Integer id) 
	{
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try 
		{
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if(rs.next())
			{
				Departamento dep = instanciar(rs);
				return dep;
			}
				return null;
		}
		catch(SQLException e) 
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
	public List<Departamento> econtreTodos() 
	{
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Departamento> lista = new ArrayList<Departamento>();
		
		try
		{
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
			
			rs = st.executeQuery();
			
			while(rs.next())
			{
				Departamento dep = instanciar(rs);
				lista.add(dep);
				
			}
			return lista;
			
		}
		catch(SQLException e)
		{
			throw new DbException(e.getMessage());
		}
		finally
		{
			DB.closeStatement(st);
			DB.closeResult(rs);
		}
	}
	
	private Departamento instanciar(ResultSet rs) throws SQLException 
	{
		String nome;
		int id;
		
		nome = rs.getString("Name");
		id = rs.getInt("Id");
		
		return new Departamento(id,nome);
	}

}
