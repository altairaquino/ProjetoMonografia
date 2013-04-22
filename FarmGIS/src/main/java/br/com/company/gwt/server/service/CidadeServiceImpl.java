package br.com.company.gwt.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.vividsolutions.jts.geom.Point;

import br.com.company.gwt.client.CidadeService;
import br.com.company.gwt.client.dto.DTOCidade;
import br.com.company.gwt.client.dto.DTOLatLng;
import br.com.company.gwt.server.InputServletImpl;
import br.com.company.gwt.server.dao.DaoCidade;
import br.com.company.gwt.server.entities.Cidade;

@Named("cidadeService")
public class CidadeServiceImpl extends InputServletImpl implements CidadeService {

	@Inject DaoCidade daoCidade;

	@Override
	public List<DTOCidade> listAll() {
		List<DTOCidade> cidades = new ArrayList<DTOCidade>();
		for(Cidade cidade: daoCidade.loadAll()){
			DTOCidade dtoCidade = new DTOCidade();
			dtoCidade.setId(cidade.getId());
			dtoCidade.setNome(cidade.getName());
			if (cidade.getTheGeom() != null){
				Point point = cidade.getTheGeom().getCentroid();
				dtoCidade.setTheGeom(new DTOLatLng(point.getY(), point.getX()));
			}
			cidades.add(dtoCidade);
		}
		return cidades;
	}	

}