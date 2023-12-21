package dao;

import java.util.List;

import entidades.Departamento;
import entidades.Vendedor;

public interface VendedorDao {

	void inserir(Vendedor obj);
	void update(Vendedor obj);
	void deletePorId(Integer id);
	Vendedor achePorId(Integer id);
	List<Vendedor> econtreTodos();
	List<Vendedor> achePorDep(Departamento dep);

}
