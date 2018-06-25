package br.com.lojavirtual.persistencia;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lojavirtual.bean.Pedido;

public class PedidoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void inserir(Pedido ped) {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
	
		try {
		
		Transaction t = sessao.beginTransaction();
		sessao.save(ped);
		t.commit();
		
		} finally {
			sessao.close();
		}
	}
}
