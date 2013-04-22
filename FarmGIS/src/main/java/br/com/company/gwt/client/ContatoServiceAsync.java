package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.shared.dto.DTOContato;
import br.com.company.gwt.shared.dto.DTOUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContatoServiceAsync {	
	
	void list(DTOUser user, AsyncCallback<List<DTOContato>> callback);

}