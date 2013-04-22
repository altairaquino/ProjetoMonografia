package br.com.company.gwt.server.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.transaction.annotation.Transactional;

import br.com.company.gwt.client.PropriedadeService;
import br.com.company.gwt.client.dto.DTOCidade;
import br.com.company.gwt.client.dto.DTOLatLng;
import br.com.company.gwt.client.dto.DTOPropriedade;
import br.com.company.gwt.server.InputServletImpl;
import br.com.company.gwt.server.dao.DaoCidade;
import br.com.company.gwt.server.dao.DaoPropriedadeRural;
import br.com.company.gwt.server.entities.Cidade;
import br.com.company.gwt.server.entities.PropriedadeRural;
import br.com.company.gwt.shared.dto.DTOConsulta03;
import br.com.company.gwt.shared.dto.DTOConsulta05;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;

@Named("propriedadeService")
public class PropriedadeServiceImpl extends InputServletImpl implements PropriedadeService {

	@Inject DaoPropriedadeRural daoPropriedadeRural;
	@Inject DaoCidade daoCidade;

	@Override
	public List<DTOPropriedade> listAll() {
		List<DTOPropriedade> propriedades = new ArrayList<DTOPropriedade>();
		for(PropriedadeRural prop: daoPropriedadeRural.loadAll()){
			DTOPropriedade dtoPropriedade = new DTOPropriedade();
			dtoPropriedade.setId(prop.getId());
			dtoPropriedade.setNome(prop.getName());
			if (prop.getCidade() != null){
				Cidade cidade = prop.getCidade();
				DTOCidade dtoCidade = new DTOCidade();
				dtoCidade.setId(cidade.getId());
				dtoCidade.setNome(cidade.getName());
				if (cidade.getTheGeom() != null){
					Point point = cidade.getTheGeom().getCentroid();
					dtoCidade.setTheGeom(new DTOLatLng(point.getY(), point.getX()));
				}
				dtoPropriedade.setDtoCidade(dtoCidade);
			}
			if (prop.getTheGeom() != null){
				Geometry geometry = prop.getTheGeom();
				List<DTOLatLng> latLngs = new ArrayList<DTOLatLng>(); 
				for (Coordinate latLong : geometry.getCoordinates()) {
					latLngs.add(new DTOLatLng(latLong.y, latLong.x));
				}
				dtoPropriedade.setTheGeom(latLngs);
				Point point = geometry.getCentroid();
				dtoPropriedade.setCentroid(new DTOLatLng(point.getY(), point.getX()));
			}
			propriedades.add(dtoPropriedade);
		}
		return propriedades;
	}
	
	@Transactional
	@Override
	public Boolean save(DTOPropriedade dtoPropriedade) {
		PropriedadeRural prop = new PropriedadeRural();
		prop.setId(dtoPropriedade.getId());
		prop.setName(dtoPropriedade.getNome());
		if (dtoPropriedade.getDtoCidade() != null) {
			Cidade cidade = daoCidade.findByPrimaryKey(dtoPropriedade.getDtoCidade().getId());
			prop.setCidade(cidade);
		}
		if (dtoPropriedade.getTheGeom() != null){
			Coordinate coordinates[] = new Coordinate[dtoPropriedade.getTheGeom().size()];
			int i = 0;
			for (DTOLatLng latLong : dtoPropriedade.getTheGeom()) {
				coordinates[i++] = new Coordinate(latLong.getLongitude(), latLong.getLatitude());
			}
			GeometryFactory factory = new GeometryFactory(new PrecisionModel(PrecisionModel.FIXED), 4326);
			
			LinearRing linearRing = factory.createLinearRing(coordinates);
			Polygon polygon = factory.createPolygon(linearRing, null);
			prop.setTheGeom(polygon);
		}
		daoPropriedadeRural.store(prop);
		return false;
	}
	
	@Override
	public List<DTOConsulta03> getDadosConsulta03(){
		List<DTOConsulta03> list = new ArrayList<DTOConsulta03>();
		
		try {
			String query = " SELECT pr.id as propriedadeId, pr.nome as propriedade," +
					" (st_area(st_transform(pr.the_geom, 900913)) / 10000) as ha_prop," +
					" (select sum(st_area(st_transform(p.the_geom, 900913))) / 10000" +
					" FROM parcela p" +
					" WHERE p.id_propriedade = pr.id)  as ha_plantados," +
					" 	(select sum(st_area(st_transform(p.the_geom, 900913))) / 10000" +
					" FROM parcela p where p.id_propriedade = pr.id)  /   (st_area(st_transform(pr.the_geom, 900913)) / 10000) * 100 as percentual" +
					" FROM propriedade_rural pr" +
					" ORDER BY propriedade";
			
			SQLQuery q = daoCidade.getSession().createSQLQuery(query);
			q.addScalar("propriedadeId", Hibernate.INTEGER);
			q.addScalar("propriedade", Hibernate.STRING);
			q.addScalar("ha_prop", Hibernate.DOUBLE);
			q.addScalar("ha_plantados", Hibernate.DOUBLE);
			q.addScalar("percentual", Hibernate.DOUBLE);
			
			for (Object[] dtoConsulta03 : ((List<Object[]>)q.list())) {
				DTOConsulta03 consulta03 = new DTOConsulta03();
				consulta03.setPropriedadeId((Integer)dtoConsulta03[0]);
				consulta03.setPropriedade((String)dtoConsulta03[1]);
				consulta03.setHectaresPropriedade((Double)dtoConsulta03[2]);
				consulta03.setHectaresPlantados((Double)dtoConsulta03[3]);
				consulta03.setPercentual((Double)dtoConsulta03[4]);
				list.add(consulta03);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return list;
	}
	
	@Override
	public List<DTOConsulta05> getDadosConsulta05(){
		List<DTOConsulta05> list = new ArrayList<DTOConsulta05>();
		
		try {
			String query = " SELECT " +
						   "	   ct.nome AS cultura_plantada," +
						   "	   ct.cor_mapa as color, " +
						   "       (sum(st_area(st_transform(p.the_geom, 900913)))/ 10000) AS ha_cultura" +
						   " FROM parcela p, cultura ct" +
						   " WHERE p.id_cultura = ct.id" +
						   " GROUP BY cultura_plantada, color" +
						   " ORDER BY cultura_plantada";
			
			SQLQuery q = daoCidade.getSession().createSQLQuery(query);
			q.addScalar("cultura_plantada", Hibernate.STRING);
			q.addScalar("ha_cultura", Hibernate.DOUBLE);
			q.addScalar("color", Hibernate.STRING);
			
			for (Object[] dtoConsulta05 : ((List<Object[]>)q.list())) {
				DTOConsulta05 consulta03 = new DTOConsulta05();
				consulta03.setCultura((String)dtoConsulta05[0]);
				consulta03.setHAPlantados((Double)dtoConsulta05[1]);
				consulta03.setColor((String)dtoConsulta05[2]);
				list.add(consulta03);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public DTOPropriedade getById(Integer propriedadeId) {
		DTOPropriedade dtoPropriedade = new DTOPropriedade();
		PropriedadeRural prop = daoPropriedadeRural.findByPrimaryKey(propriedadeId);
		if(prop != null){
			dtoPropriedade.setId(prop.getId());
			dtoPropriedade.setNome(prop.getName());
			if (prop.getCidade() != null){
				Cidade cidade = prop.getCidade();
				DTOCidade dtoCidade = new DTOCidade();
				dtoCidade.setId(cidade.getId());
				dtoCidade.setNome(cidade.getName());
				if (cidade.getTheGeom() != null){
					Point point = cidade.getTheGeom().getCentroid();
					dtoCidade.setTheGeom(new DTOLatLng(point.getY(), point.getX()));
				}
				dtoPropriedade.setDtoCidade(dtoCidade);
			}
			if (prop.getTheGeom() != null){
				Geometry geometry = prop.getTheGeom();
				List<DTOLatLng> latLngs = new ArrayList<DTOLatLng>();
				for (Coordinate latLong : geometry.getCoordinates()) {
					latLngs.add(new DTOLatLng(latLong.y, latLong.x));
				}
				dtoPropriedade.setTheGeom(latLngs);
				Point point = geometry.getCentroid();
				dtoPropriedade.setCentroid(new DTOLatLng(point.getY(), point.getX()));
			}
		}
		return dtoPropriedade;
	}
		
}