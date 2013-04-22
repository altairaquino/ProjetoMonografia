package br.com.company.gwt.shared.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOConsulta03 extends BaseModelData {

	private static final long serialVersionUID = 1615203326282152918L;
	
	public void setPropriedadeId(Integer propriedadeId) {
		this.set("propriedadeId", propriedadeId);
	}
	
	public Integer getPropriedadeId() {
		return this.get("propriedadeId");
	}
	
	public void setPropriedade(String propriedade) {
		this.set("propriedade", propriedade);
	}

	public String getPropriedade() {
		return get("propriedade");
	}

	public void setHectaresPropriedade(Double hectaresPropriedade) {
		this.set("hectaresPropriedade", hectaresPropriedade);
	}

	public Double getHectaresPropriedade() {
		return get("hectaresPropriedade");
	}

	public void setHectaresPlantados(Double hectaresPlantados) {
		this.set("hectaresPlantados", hectaresPlantados);
	}

	public Double getHectaresPlantados() {
		return get("hectaresPlantados");
	}

	public void setPercentual(Double percentual) {
		this.set("percentual", percentual);
	}

	public Double getPercentual() {
		return get("percentual");
	}		

}