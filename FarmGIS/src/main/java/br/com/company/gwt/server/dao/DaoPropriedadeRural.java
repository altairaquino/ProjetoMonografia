package br.com.company.gwt.server.dao;

import javax.inject.Named;

import br.com.company.gwt.server.entities.PropriedadeRural;

@Named(value="daoPropriedadeRural")
public class DaoPropriedadeRural extends DaoAbstract<PropriedadeRural, Integer> {
	
	@Override
	protected Integer getId(PropriedadeRural propriedade) {
		return propriedade.getId();
	}

}