package br.com.lojavirtual.negocio;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.lojavirtual.bean.Pessoa;
import br.com.lojavirtual.persistencia.PessoaDAO;

@ManagedBean
@SessionScoped
public class UsuarioCtrl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Pessoa pessoa;

	public UsuarioCtrl() {
		pessoa = new Pessoa();

		SecurityContext context = SecurityContextHolder.getContext();
		if (context instanceof SecurityContext) {
			Authentication authentication = context.getAuthentication();
			if (authentication instanceof Authentication) {
				pessoa.setUsuario(((User) authentication.getPrincipal())
						.getUsername());
			}
		}
	}

	public String getUsuario() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getListagemP() {
		return PessoaDAO.listagemPerfil(pessoa.getUsuario());
	}

}
