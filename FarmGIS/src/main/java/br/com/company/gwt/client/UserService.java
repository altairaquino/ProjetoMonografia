package br.com.company.gwt.client;

import br.com.company.gwt.shared.dto.DTOUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("services/userService")
public interface UserService extends RemoteService {
	
	public Boolean login(DTOUser user);	
	
}