package br.com.lojavirtual.negocio;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.lojavirtual.bean.Produto;
import br.com.lojavirtual.persistencia.ProdutoDAO;

@ManagedBean(name = "produtoCtrl")
@SessionScoped
public class ProdutoCtrl implements Serializable {

	private static final long serialVersionUID = 1L;
	private Produto produto;
	private String filtro;

	public Produto getProduto() {
		return this.produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListagem() {
		return ProdutoDAO.listagem(this.filtro);
	}

	public String actionGravar() {
		if (this.produto.getId() == 0) {
			ProdutoDAO.inserir(this.produto);
			this.addMessage("Produto Gravado Com Sucesso!");
			return this.actionInserir();
		} else {
			ProdutoDAO.alterar(this.produto);
			return "/admin/lista_produto?faces-redirect=true";
		}
	}

	public String actionInserir() {
		this.produto = new Produto();
		return "admin/form_produto?faces-redirect=true";
	}

	public String actionExcluir(Produto p) {
		ProdutoDAO.excluir(p);
		return "/admin/lista_produto?faces-redirect=true";
	}

	public String actionAlterar(Produto p) {
		this.produto = p;
		return "/admin/form_produto?faces-redirect=true";
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String getFiltro() {
		return this.filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Sucesso", event.getFile().getFileName() + " foi enviado!");
		FacesContext.getCurrentInstance().addMessage(null, message);
		this.produto.setUrl_image(event.getFile().getFileName());
	}

}
