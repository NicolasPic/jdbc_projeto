package dao;

import java.util.List;

import entidades.Departamento;

public interface DepartamentoDao {
	
	void inserir(Departamento obj);
	void update(Departamento obj);
	void deletePorId(Integer id);
	Departamento achePorId(Integer id);
	List<Departamento> econtreTodos();

}
