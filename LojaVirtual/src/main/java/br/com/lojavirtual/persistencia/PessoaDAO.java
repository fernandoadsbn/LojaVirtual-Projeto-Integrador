package br.com.lojavirtual.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import org.apache.poi.xslf.model.geom.Expression;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.lojavirtual.bean.Fone;
import br.com.lojavirtual.bean.Pessoa;
import br.com.lojavirtual.bean.Produto;

public class PessoaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void inserir(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(pessoa);
		t.commit();
		sessao.close();
	}

	public static void alterar(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.update(pessoa);
		t.commit();
		sessao.close();
	}

	public static void excluir(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.delete(pessoa);
		t.commit();
		sessao.close();
	}

	public static List<Pessoa> listagem() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		consulta = sessao.createQuery("from Pessoa order by pes_nome");
		List lista = consulta.list();
		sessao.close();
		return lista;
	}

	public static void excluirFone(Fone fone) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.delete(fone);
		t.commit();
		sessao.close();
	}

	public static List<Pessoa> listagemPerfil(String usuario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		consulta = sessao.createQuery("from Pessoa where pes_email = '"
				+ usuario + "'");
		List lista = consulta.list();
		sessao.close();
		return lista;
	}

	public static Pessoa pesqUsuario(String usuario) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Query consulta = sessao
					.createQuery("from Pessoa where pes_email = :parametro");
			consulta.setString("parametro", usuario);
			return (Pessoa) consulta.uniqueResult();
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
