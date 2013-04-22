package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOCultura;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CulturaServiceAsync {

	void listAll(AsyncCallback<List<DTOCultura>> callback);

	void save(DTOCultura model, AsyncCallback<Boolean> salvaParcelaAsync);

}