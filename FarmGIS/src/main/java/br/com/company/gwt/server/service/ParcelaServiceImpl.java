package br.com.company.gwt.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.company.gwt.client.ParcelaService;
import br.com.company.gwt.client.dto.DTOCultura;
import br.com.company.gwt.client.dto.DTOLatLng;
import br.com.company.gwt.client.dto.DTOParcela;
import br.com.company.gwt.client.dto.DTOPropriedade;
import br.com.company.gwt.server.InputServletImpl;
import br.com.company.gwt.server.dao.DaoCultura;
import br.com.company.gwt.server.dao.DaoParcela;
import br.com.company.gwt.server.dao.DaoPropriedadeRural;
import br.com.company.gwt.server.entities.Cultura;
import br.com.company.gwt.server.entities.Parcela;
import br.com.company.gwt.server.entities.PropriedadeRural;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;

@Named("parcelaService")
public class ParcelaServiceImpl extends InputServletImpl implements ParcelaService {

	@Inject DaoParcela daoParcela;
	@Inject DaoPropriedadeRural daoPropriedadeRural;
	@Inject DaoCultura daoCultura;

	@Override
	public List<DTOParcela> listAll() {
		List<DTOParcela> propriedades = new ArrayList<DTOParcela>();
		for(Parcela parcela: daoParcela.loadAll()){
			propriedades.add(parcelaToDto(parcela));
		}
		return propriedades;
	}
	
	@Override
	public List<DTOParcela> listByPropriedade(Integer IdPropriedade) {
		List<DTOParcela> propriedades = new ArrayList<DTOParcela>();
		for(Parcela parcela: daoParcela.listByPropriedade(IdPropriedade)){
			propriedades.add(parcelaToDto(parcela));
		}
		return propriedades;
	}
	
	@Override
	public List<DTOParcela> listByCultura(Integer IdCultura) {
		List<DTOParcela> propriedades = new ArrayList<DTOParcela>();
		for(Parcela parcela: daoParcela.listByCultura(IdCultura)){
			propriedades.add(parcelaToDto(parcela));
		}
		return propriedades;
	}
	
	@Transactional
	@Override
	public Boolean save(DTOParcela dtoParcela) {
		boolean ret = true;
		try{
			Parcela parcela = new Parcela();
			parcela.setId(dtoParcela.getId());
			parcela.setCodigo(dtoParcela.getCodigo().trim());
			if (dtoParcela.getDTOPropriedade() != null){
				PropriedadeRural propried = daoPropriedadeRural.findByPrimaryKey(dtoParcela.getDTOPropriedade().getId());
				parcela.setPropriedade(propried);
			}
			if (dtoParcela.getDTOCultura() != null){
				Cultura cultura = daoCultura.findByPrimaryKey(dtoParcela.getDTOCultura().getId());
				parcela.setCultura(cultura);			
			}
			if (dtoParcela.getTheGeom() != null){
				Coordinate coordinates[] = new Coordinate[dtoParcela.getTheGeom().size()];
				int i = 0;
				for (DTOLatLng latLong : dtoParcela.getTheGeom()) {
					coordinates[i++] = new Coordinate(latLong.getLongitude(), latLong.getLatitude());
				}
				GeometryFactory factory = new GeometryFactory(new PrecisionModel(PrecisionModel.FIXED), 4326);
				
				LinearRing linearRing = factory.createLinearRing(coordinates);
				Polygon polygon = factory.createPolygon(linearRing, null);
				parcela.setTheGeom(polygon);
			}
			return daoParcela.store(parcela) != null;
			
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return ret;
	}
	
	private DTOCultura culturaToDto(Cultura cultura){
		DTOCultura dtoCultura = new DTOCultura();
		dtoCultura.setId(cultura.getId());
		dtoCultura.setNome(cultura.getNome());
		dtoCultura.setCorMapa(cultura.getCorMapa());
		return dtoCultura;
	}
	private DTOPropriedade propriedadeToDto(PropriedadeRural propriedade){
		DTOPropriedade dtoPropriedade = new DTOPropriedade();
		dtoPropriedade.setId(propriedade.getId());
		dtoPropriedade.setNome(propriedade.getName());
		if (propriedade.getTheGeom() != null){
			Geometry geometry = propriedade.getTheGeom();
			List<DTOLatLng> latLngs = new ArrayList<DTOLatLng>(); 
			for (Coordinate latLong : geometry.getCoordinates()) {
				latLngs.add(new DTOLatLng(latLong.y, latLong.x));
			}
			dtoPropriedade.setTheGeom(latLngs);
			Point point = geometry.getCentroid();
			dtoPropriedade.setCentroid(new DTOLatLng(point.getY(), point.getX()));
		}
		return dtoPropriedade;
	}
	
	private DTOParcela parcelaToDto(Parcela parcela){
		DTOParcela dtoParcela = new DTOParcela();
		dtoParcela.setId(parcela.getId());
		dtoParcela.setCodigo(parcela.getCodigo());
		if (parcela.getPropriedade() != null){
			dtoParcela.setDTOPropriedade(propriedadeToDto(parcela.getPropriedade()));
		}
		if (parcela.getCultura() != null){
			dtoParcela.setDTOCultura(culturaToDto(parcela.getCultura()));
		}
		if (parcela.getTheGeom() != null){
			Geometry geometry = parcela.getTheGeom();
			List<DTOLatLng> latLngs = new ArrayList<DTOLatLng>(); 
			for (Coordinate latLong : geometry.getCoordinates()) {
				latLngs.add(new DTOLatLng(latLong.y, latLong.x));
			}
			dtoParcela.setTheGeom(latLngs);
			Point point = geometry.getCentroid();
			dtoParcela.setCentroid(new DTOLatLng(point.getY(), point.getX()));
		}
		return dtoParcela;
	}
		
}