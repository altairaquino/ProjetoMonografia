package br.com.company.gwt.shared.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOUsuario extends BaseModelData{
	
	private static final long serialVersionUID = -7101847679210286297L;
	
	public String getUsncgep() {
		return get("usncgep");
	}
	public void setUsncgep(String usncgep) {
		this.set("usncgep", usncgep);
	}
	public String getUscnmep() {
		return get("uscnmep");
	}
	public void setUscnmep(String uscnmep) {
		this.set("uscnmep", uscnmep);
	}
	public String getUsncodg() {
		return get("usncodg");
	}
	public void setUsncodg(String usncodg) {
		this.set("usncodg", usncodg);
	}
	public String getUscnome() {
		return get("uscnome");
	}
	public void setUscnome(String uscnome) {
		this.set("uscnome", uscnome);
	}
	public String getUsclogn() {
		return get("usclogn");
	}
	public void setUsclogn(String usclogn) {
		this.set("usclogn", usclogn);
	}
	
	public String getUscsenh() {
		return get("uscsenh");
	}
	public void setUscsenh(String uscsenh) {
		this.set("uscsenh", uscsenh);
	}
	public String getUslativ() {
		return get("uslativ");
	}
	public void setUslativ(String uslativ) {
		this.set("uslativ", uslativ);
	}

}