package br.com.lojavirtual.negocio;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.lojavirtual.bean.Fone;
import br.com.lojavirtual.bean.Pessoa;
import br.com.lojavirtual.persistencia.PessoaDAO;

@ManagedBean
@SessionScoped
public class PessoaCtrl implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa;
	private Fone fone;
	private int id;

	public Fone getFone() {
		return this.fone;
	}

	public void setFone(Fone fone) {
		this.fone = fone;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getListagem() {
		return PessoaDAO.listagem();
	}

	public String actionGravar() {
		if (this.pessoa.getId() == 0) {
			this.pessoa.setPermissao("ROLE_USER");
			PessoaDAO.inserir(this.pessoa);
			this.addMessage("Usuário Gravado Com Sucesso!");
			return this.actionInserir();
		} else {
			PessoaDAO.alterar(this.pessoa);
			return "/admin/lista_usuario?faces-redirect=true";
		}
	}

	public String actionInserir() {
		this.pessoa = new Pessoa();
		return "/publico/cadastrar_usuario";
	}

	public String actionExcluir(Pessoa p) {
		PessoaDAO.excluir(p);
		return "/admin/lista_usuario?faces-redirect=true";
	}

	public String actionAlterar(Pessoa p) {
		this.pessoa = p;
		return "/publico/cadastrar_usuario?faces-redirect=true";
	}

	public String actionInserirFone() {
		Fone fone = new Fone();
		fone.setPessoa(this.pessoa);
		this.pessoa.getFones().add(fone);
		return "/publico/cadastrar_usuario?faces-redirect=true";
	}

	public String actionExcluirFone(Fone f) {
		f.setPessoa(this.pessoa);
		this.pessoa.getFones().remove(f);
		PessoaDAO.excluirFone(f);
		return "/publico/cadastrar_usuario?faces-redirect=true";
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}