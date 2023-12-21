package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Vendedor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String email;
	private Date  birthDate;
	private Double salarioBase;
	private Departamento departamento;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Double getSalarioBase() {
		return salarioBase;
	}
	public void setSalarioBase(Double salarioBase) {
		this.salarioBase = salarioBase;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public Vendedor(Integer id, String nome, String email, Date birthDate,Double salarioBase,Departamento departamento) {
		this.id = id;
		this.nome = nome;
		this.email = email;	
		this.birthDate = birthDate;
		this.salarioBase = salarioBase;	
		this.departamento = departamento;
	}

	public Vendedor() {
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendedor other = (Vendedor) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "Vendendor [id=" + id + ", nome=" + nome + ", email=" + email + ", birthDate=" + birthDate
				+ ", salarioBase=" + salarioBase + ", departamento=" + departamento + "]";
	}
	

}


