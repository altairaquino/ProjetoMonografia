package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOCultura;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("services/culturaService")
public interface CulturaService extends RemoteService {

	List<DTOCultura> listAll();

	boolean save(DTOCultura model);
	
}