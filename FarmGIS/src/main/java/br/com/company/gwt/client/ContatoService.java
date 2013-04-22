package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.shared.dto.DTOContato;
import br.com.company.gwt.shared.dto.DTOUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("services/contatoService")
public interface ContatoService extends RemoteService {
	
	public List<DTOContato> list(DTOUser user);
	
}