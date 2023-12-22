package programa;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.FabricaDao;
import dao.VendedorDao;
import entidades.Departamento;
import entidades.Vendedor;

public class MainVendedor {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		VendedorDao vendedorDao = FabricaDao.criarVendedorDao();
		
		System.out.println("=== TEST 1: vendedor achePorId ===");
		Vendedor vend = vendedorDao.achePorId(3);
		System.out.println(vend);
		
		System.out.println("\n=== TEST2: vendedor achePorDep ===");
		Departamento dep = new Departamento(2,null);
		List<Vendedor> lista = vendedorDao.achePorDep(dep);
		for(Vendedor obj : lista) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST3: vendedor acheTotal ===");
		lista = vendedorDao.econtreTodos();
		for(Vendedor obj : lista) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST4: vendedor inserir ===");
		Vendedor nS = new Vendedor(null,"Greg", "greeg@gmail.com",new Date(),4000.00, dep);
		vendedorDao.inserir(nS);
		System.out.println("Inserido! novo id = " + nS.getId());
		
		System.out.println("\n=== TEST5: vendedor inserir ===");
		vend = vendedorDao.achePorId(3);
		vend.setNome("Martha");
		vendedorDao.update(vend);
		System.out.println("atualizado");
		
		System.out.println("\n=== TEST6: vendedor deletar ===");
		System.out.println("Digite um id:");
		int id = sc.nextInt();
		vendedorDao.deletePorId(id);
		System.out.println("Delete completo");
		
		sc.close();
	}

}
