package br.com.lojavirtual.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pessoa")
public class Pessoa {
	
	@Id
	@GeneratedValue
	@Column(name = "pes_id")
	private int id;
	@Column(name = "pes_nome", length = 60, nullable = true)
	private String nome;
	@Column(name = "pes_cpf", length = 14, nullable = true)
	private String cpf;
	@Column(name = "pes_rg", length = 20, nullable = true)
	private String rg;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pes_data_nasc")
	private Date data_nascimento;
	@Column(name = "pes_rua", length = 60, nullable = true)
	private String rua;
	@Column(name = "pes_bairro", length = 30, nullable = true)
	private String bairro;
	@Column(name = "pes_cidade", length = 30, nullable = true)
	private String cidade;
	@Column(name = "pes_uf", length = 2, nullable = true)
	private String uf;
	@Column(name = "pes_cep")
	private int cep;
	@Column(name = "pes_email", length = 40)
	private String email;
	@Column(name = "pes_senha", length = 32, nullable = true)
	private String senha;
	@Column(name = "pes_usuario", nullable = true)
	private String usuario;
	@Column(name = "pes_permissao", length = 10)
	private String permissao;

	
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Fone> fones = new ArrayList<Fone>();

	public List<Fone> getFones() {
		return fones;
	}

	public void setFones(List<Fone> fones) {
		this.fones = fones;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

}