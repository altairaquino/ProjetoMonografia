package br.com.company.gwt.server.dao;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Query;

import br.com.company.gwt.server.entities.Parcela;

@Named(value="daoParcela")
public class DaoParcela extends DaoAbstract<Parcela, Integer> {
	
	@Override
	protected Integer getId(Parcela parcela) {
		return parcela.getId();
	}

	@SuppressWarnings("unchecked")
	public List<Parcela> listByPropriedade(Integer idPropriedade) {
		String hql = " from Parcela p" +
				     " where p.propriedade.id = :idPropriedade";
		
		Query query = createQuery(hql);
		query.setParameter("idPropriedade", idPropriedade);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Parcela> listByCultura(Integer idCultura) {
		String hql = " from Parcela p" +
	     " where p.cultura.id = :idCultura";

		Query query = createQuery(hql);
		query.setParameter("idCultura", idCultura);
		return query.list();
	}

}