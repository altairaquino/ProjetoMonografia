package br.com.company.gwt.server.dao;

import javax.inject.Named;

import br.com.company.gwt.server.entities.Cidade;

@Named(value="daoCidade")
public class DaoCidade extends DaoAbstract<Cidade, Integer> {
	
	@Override
	protected Integer getId(Cidade cidade) {
		return cidade.getId();
	}

}