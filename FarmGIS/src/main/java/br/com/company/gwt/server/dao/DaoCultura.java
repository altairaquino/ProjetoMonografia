package br.com.company.gwt.server.dao;

import javax.inject.Named;

import br.com.company.gwt.server.entities.Cultura;

@Named(value="daoCultura")
public class DaoCultura extends DaoAbstract<Cultura, Integer> {
	
	@Override
	protected Integer getId(Cultura cultura) {
		return cultura.getId();
	}

}