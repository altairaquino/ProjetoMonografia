package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOParcela;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("services/parcelaService")
public interface ParcelaService extends RemoteService {

	List<DTOParcela> listAll();

	Boolean save(DTOParcela dtoParcela);

	List<DTOParcela> listByPropriedade(Integer IdPropriedade);

	List<DTOParcela> listByCultura(Integer IdCultura);
	
}