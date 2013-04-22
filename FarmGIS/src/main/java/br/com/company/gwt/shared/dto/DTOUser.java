package br.com.company.gwt.shared.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class DTOUser extends BaseModelData {

	private static final long serialVersionUID = 2547977878755738910L;
	
	public String getUserName() {
		return get("userName");
	}
	public void setUserName(String userName) {
		set("userName", userName);
	}
	public String getPassword() {
		return get("password");
	}
	public void setPassword(String password) {
		set("password", password);
	}	

}