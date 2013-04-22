package br.com.company.gwt.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.company.gwt.client.CulturaService;
import br.com.company.gwt.client.dto.DTOCultura;
import br.com.company.gwt.server.InputServletImpl;
import br.com.company.gwt.server.dao.DaoCultura;
import br.com.company.gwt.server.entities.Cultura;

@Named("culturaService")
public class CulturaServiceImpl extends InputServletImpl implements CulturaService {

	@Inject DaoCultura daoCultura;

	@Override
	public List<DTOCultura> listAll() {
		List<DTOCultura> culturas = new ArrayList<DTOCultura>();
		for(Cultura cultura: daoCultura.loadAll()){
			DTOCultura dtoCultura = new DTOCultura();
			dtoCultura.setId(cultura.getId());
			dtoCultura.setNome(cultura.getNome());
			dtoCultura.setCorMapa(cultura.getCorMapa());
			culturas.add(dtoCultura);
		}
		return culturas;
	}

	@Transactional
	@Override
	public boolean save(DTOCultura dtoCultura) {
		Cultura cultura = new Cultura();
		cultura.setId(dtoCultura.getId());
		cultura.setNome(dtoCultura.getNome());
		cultura.setCorMapa(dtoCultura.getCorMapa());
		return daoCultura.store(cultura) != null;
	}	

}