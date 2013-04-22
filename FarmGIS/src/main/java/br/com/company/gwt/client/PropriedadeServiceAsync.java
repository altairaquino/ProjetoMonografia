package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOPropriedade;
import br.com.company.gwt.shared.dto.DTOConsulta03;
import br.com.company.gwt.shared.dto.DTOConsulta05;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PropriedadeServiceAsync {
	
	void listAll(AsyncCallback<List<DTOPropriedade>> callback);

	void save(DTOPropriedade dtoPropriedade, AsyncCallback<Boolean> callback);

	void getDadosConsulta03(AsyncCallback<List<DTOConsulta03>> callback);

	void getById(Integer propriedadeId, AsyncCallback<DTOPropriedade> callback);

	void getDadosConsulta05(AsyncCallback<List<DTOConsulta05>> callback);

}