package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOCidade;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("services/cidadeService")
public interface CidadeService extends RemoteService {

	List<DTOCidade> listAll();
	
}