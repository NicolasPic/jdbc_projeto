package programa;

import java.util.List;
import java.util.Scanner;

import dao.DepartamentoDao;
import dao.FabricaDao;
import entidades.Departamento;

public class MainDepartamento {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		DepartamentoDao departamentoDao = FabricaDao.criarDepartamentoDao();

		System.out.println("=== TEST 1: achePorId =======");
		Departamento dep = departamentoDao.achePorId(1);
		System.out.println(dep);
		
		System.out.println("\n=== TEST 2: encontreTodos =======");
		List<Departamento> list = departamentoDao.econtreTodos();
		for (Departamento d : list) {
			System.out.println(d);
		}

		System.out.println("\n=== TEST 3: inserir =======");
		Departamento newDepartamento = new Departamento(null, "Musica");
		departamentoDao.inserir(newDepartamento);
		System.out.println("Inserido com sucesso: " + newDepartamento.getId());

		System.out.println("\n=== TEST 4: update =======");
		Departamento dep2 = departamentoDao.achePorId(1);
		dep2.setNome("comida");
		departamentoDao.update(dep2);
		System.out.println("Update completo");
		
		System.out.println("\n=== TEST 5: delete =======");
		System.out.print("Enter id para testar:: ");
		int id = sc.nextInt();
		departamentoDao.deletePorId(id);
		System.out.println("Delete completado");

		sc.close();
	}
}
	


