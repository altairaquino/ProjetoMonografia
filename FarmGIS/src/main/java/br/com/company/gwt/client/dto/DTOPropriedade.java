package br.com.company.gwt.client.dto;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOPropriedade extends BaseModelData{

	private static final long serialVersionUID = 5083203438232635915L;
	
	private List<DTOLatLng> theGeom;
	private DTOCidade cidade;
	private DTOLatLng centroid;
	
	public Integer getId() {
		return get("id");
	}

	public void setId(Integer id) {
		set("id", id);
	}	

	public void setNome(String nome) {
		set("nome", nome);
	}

	public String getNome() {
		return get("nome");
	}

	public void setDtoCidade(DTOCidade dtoCidade) {
		set("dtoCidade", dtoCidade);
	}

	public DTOCidade getDtoCidade() {
		return get("dtoCidade");
	}

	public void setTheGeom(List<DTOLatLng> theGeom) {
		this.theGeom = theGeom;
	}

	public List<DTOLatLng> getTheGeom() {
		return theGeom;
	}

	public void setCentroid(DTOLatLng centroid) {
		this.centroid = centroid;
	}

	public DTOLatLng getCentroid() {
		return centroid;
	}
	
}