package br.com.company.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOCidade extends BaseModelData{

	private static final long serialVersionUID = 5083203438232635915L;
	
	private DTOLatLng theGeom;

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

	public void setTheGeom(DTOLatLng theGeom) {
		this.theGeom = theGeom;
	}

	public DTOLatLng getTheGeom() {
		return theGeom;
	}
	
}