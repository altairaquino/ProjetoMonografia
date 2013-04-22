package br.com.company.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOCultura extends BaseModelData{

	private static final long serialVersionUID = 5083203438232635915L;

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

	public void setCorMapa(String corMapa) {
		set("corMapa", corMapa);
	}

	public String getCorMapa() {
		return get("corMapa");
	}

}