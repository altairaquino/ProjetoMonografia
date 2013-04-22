package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOParcela;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ParcelaServiceAsync {
	
	void listAll(AsyncCallback<List<DTOParcela>> callback);

	void save(DTOParcela dtoParcela, AsyncCallback<Boolean> callback);

	void listByPropriedade(Integer IdPropriedade,AsyncCallback<List<DTOParcela>> callback);

	void listByCultura(Integer IdCultura, AsyncCallback<List<DTOParcela>> callback);

}