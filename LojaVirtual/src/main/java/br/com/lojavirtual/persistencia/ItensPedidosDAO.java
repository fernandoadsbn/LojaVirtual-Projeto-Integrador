package br.com.lojavirtual.persistencia;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lojavirtual.bean.ItensPedidos;

public class ItensPedidosDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void inserir(ItensPedidos iten) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();

		try {
			Transaction t = sessao.beginTransaction();
			sessao.save(iten);
			t.commit();

		} finally {
			sessao.close();
		}
	}
}
