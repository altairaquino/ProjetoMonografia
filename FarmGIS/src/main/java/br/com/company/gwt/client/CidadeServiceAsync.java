package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOCidade;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CidadeServiceAsync {

	void listAll(AsyncCallback<List<DTOCidade>> callback);

}
