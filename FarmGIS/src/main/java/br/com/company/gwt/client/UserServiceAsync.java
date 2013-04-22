package br.com.company.gwt.client;

import br.com.company.gwt.shared.dto.DTOUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {	
	
	void login(DTOUser user, AsyncCallback<Boolean> callback);	

}