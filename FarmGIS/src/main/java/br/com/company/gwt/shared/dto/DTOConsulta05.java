package br.com.company.gwt.shared.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOConsulta05 extends BaseModelData {

	private static final long serialVersionUID = 1615203326282152918L;
	
	public void setCultura(String cultura) {
		this.set("cultura", cultura);
	}
	
	public String getCultura() {
		return this.get("cultura");
	}
	
	public void setHAPlantados(Double HAPlantados) {
		this.set("HAPlantados", HAPlantados);
	}

	public Double getHAPlantados() {
		return get("HAPlantados");
	}

	public String getColor() {
		return get("color");
	}
	
	public void setColor(String color) {
		this.set("color", color);
	}

}