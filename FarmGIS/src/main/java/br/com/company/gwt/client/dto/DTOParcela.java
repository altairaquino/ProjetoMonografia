package br.com.company.gwt.client.dto;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOParcela extends BaseModelData{

	private static final long serialVersionUID = 5083203438232635915L;
	
	private List<DTOLatLng> theGeom;
	private DTOPropriedade dtoPropriedade;
	private DTOCultura dtoCultura;
	private DTOLatLng centroid;
	
	public Integer getId() {
		return get("id");
	}

	public void setId(Integer id) {
		set("id", id);
	}	

	public void setCodigo(String codigo) {
		set("codigo", codigo);
	}

	public String getCodigo() {
		return get("codigo");
	}

	public void setDTOPropriedade(DTOPropriedade dtoPropriedade) {
		set("dtoPropriedade", dtoPropriedade);
	}

	public DTOPropriedade getDTOPropriedade() {
		return get("dtoPropriedade");
	}
	
	public void setDTOCultura(DTOCultura dtoCultura) {
		set("dtoCultura", dtoCultura);
	}
	
	public DTOCultura getDTOCultura() {
		return get("dtoCultura");
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