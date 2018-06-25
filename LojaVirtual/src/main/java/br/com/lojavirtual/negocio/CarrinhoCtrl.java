package br.com.lojavirtual.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lojavirtual.bean.FormaPgto;
import br.com.lojavirtual.bean.ItensPedidos;
import br.com.lojavirtual.bean.Pedido;
import br.com.lojavirtual.bean.Pessoa;
import br.com.lojavirtual.bean.Produto;
import br.com.lojavirtual.persistencia.FormaPgtoDAO;
import br.com.lojavirtual.persistencia.ItensPedidosDAO;
import br.com.lojavirtual.persistencia.PedidoDAO;
import br.com.lojavirtual.persistencia.PessoaDAO;

@ManagedBean
@SessionScoped
public class CarrinhoCtrl implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();
	private ItensPedidos itens = new ItensPedidos();
	private Pessoa pessoa = new Pessoa();
	private UsuarioCtrl usuario = new UsuarioCtrl();
	private FormaPgtoCtrl fpg = new FormaPgtoCtrl();
	private FormaPgto formaDePagamento;
	private List<FormaPgto> listaFpgt;
	private Produto produto = new Produto();
	private List<Integer> listaParcelas;
	private int qdtItens = 0;
	private int somaQtdItens = 0;
	private float somaSubTotal = 0;
	private int numeroParc;
	private ArrayList<Produto> listaProduto = new ArrayList<Produto>();
	private ArrayList<ItensPedidos> listaItensPedido = new ArrayList<ItensPedidos>();
	private String filtro = "";

	public void addCarrinho(Produto p) {
		this.addMessage("Adicionado com Sucesso!");
		this.somaSubTotal = this.somaSubTotal + p.getPreco();
		this.listaProduto.add(p);
		this.somaQtdItens = this.listaProduto.size();
		this.produto = p;
	}

	public void removeCarrinho(Produto p) {
		this.addMessage("Removido com Sucesso!");
		if (this.somaSubTotal > 0) {
			this.somaSubTotal = this.somaSubTotal - p.getPreco();
		}
		this.listaProduto.remove(p);
		this.somaQtdItens = this.listaProduto.size();
		this.produto = p;
	}

	public void actionUsuario() {
		this.listaFpgt = FormaPgtoDAO.listagem(this.filtro);
		Pessoa pes = PessoaDAO.pesqUsuario(this.getUsuarioLogado());
		this.setPessoa(pes);
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String actionPedido() {
		this.addMessage("Compra Finalizada com Sucesso!");
		Date data = new Date(System.currentTimeMillis());
		Pessoa pes = PessoaDAO.pesqUsuario(this.getUsuarioLogado());
		this.pedido.setFormaPgto(this.formaDePagamento);
		this.pedido.setPessoa(pes);
		this.pedido.setPed_dataAutorizacao(data);
		this.pedido.setPed_dataEmissao(data);
		this.pedido.setPed_status("ABERTO");
		this.pedido.setPed_total(this.somaSubTotal);
		PedidoDAO.inserir(this.pedido);

		for (Produto prod : this.listaProduto) {
			this.itens = new ItensPedidos();
			this.itens.setProd(prod);
			this.itens.setIpe_qtde(this.somaQtdItens);
			this.itens.setIpe_subtotal(this.somaSubTotal);
			this.itens.setIpe_valorUnit(prod.getPreco());
			this.itens.setProd(prod);
			this.itens.setPed(this.pedido);
			ItensPedidosDAO.inserir(this.itens);
		}
		this.pedido = new Pedido();
		this.setPessoa(new Pessoa());
		this.itens = new ItensPedidos();
		this.formaDePagamento = new FormaPgto();
		this.listaProduto = new ArrayList<>();
		this.somaSubTotal = 0;
		this.somaQtdItens = 0;
		this.listaParcelas = new ArrayList<>();
		return "/publico/index.xhtml";
	}

	public String actionTipodePgt(FormaPgto f) {
		this.formaDePagamento = f;
		this.actionParcelas();
		return "/publico/carrinho.xhtml?faces-redirect=true";
	}

	public List<Integer> actionParcelas() {
		this.listaParcelas = new ArrayList<>();
		int num = this.formaDePagamento.getNumMaxParc();
		for (int i = 1; i <= num; i++) {
			this.listaParcelas.add(i);
		}
		return this.listaParcelas;
	}

	public void actionCliente() {
		this.listaFpgt = this.getListagem();
		Pessoa pes = PessoaDAO.pesqUsuario(this.getUsuarioLogado());
		this.setPessoa(pes);
	}

	public String getUsuarioLogado() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}

	// ------GET/SET------\\

	public int getQdtItens() {
		return this.qdtItens;
	}

	public void setQdtItens(int qdtItens) {
		this.qdtItens = qdtItens;
	}

	public int getSomaQtdItens() {
		return this.somaQtdItens;
	}

	public void setSomaQtdItens(int somaQtdItens) {
		this.somaQtdItens = somaQtdItens;
	}

	public ArrayList<Produto> getListaProduto() {
		return this.listaProduto;
	}

	public void setListaProduto(ArrayList<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getNumeroParc() {
		return this.numeroParc;
	}

	public void setNumeroParc(int numeroParc) {
		this.numeroParc = numeroParc;
	}

	public UsuarioCtrl getUsuario() {
		return this.usuario;
	}

	public void setUsuario(UsuarioCtrl usuario) {
		this.usuario = usuario;
	}

	public FormaPgtoCtrl getFpg() {
		return this.fpg;
	}

	public void setFpg(FormaPgtoCtrl fpg) {
		this.fpg = fpg;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public FormaPgto getFormaDePagamento() {
		return this.formaDePagamento;
	}

	public void setFormaDePagamento(FormaPgto formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public float getSomaSubTotal() {
		return this.somaSubTotal;
	}

	public void setSomaSubTotal(float somaSubTotal) {
		this.somaSubTotal = somaSubTotal;
	}

	public ItensPedidos getItens() {
		return this.itens;
	}

	public void setItens(ItensPedidos itens) {
		this.itens = itens;
	}

	public List<FormaPgto> getListaFpgt() {
		return this.listaFpgt;
	}

	public void setListaFpgt(List<FormaPgto> listaFpgt) {
		this.listaFpgt = listaFpgt;
	}

	public List<Integer> getListaParcelas() {
		return this.listaParcelas;
	}

	public void setListaParcelas(List<Integer> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

	public FormaPgto getformaDePagamento() {
		return this.formaDePagamento;
	}

	public void setformaDePagamento(FormaPgto formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public String getFiltro() {
		return this.filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<FormaPgto> getListagem() {
		return FormaPgtoDAO.listagem(this.filtro);
	}

	public ArrayList<ItensPedidos> getListaItensPedido() {
		return this.listaItensPedido;
	}

	public void setListaItensPedido(ArrayList<ItensPedidos> listaItensPedido) {
		this.listaItensPedido = listaItensPedido;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
