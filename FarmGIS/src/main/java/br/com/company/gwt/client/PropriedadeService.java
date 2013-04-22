package br.com.company.gwt.client;

import java.util.List;

import br.com.company.gwt.client.dto.DTOPropriedade;
import br.com.company.gwt.shared.dto.DTOConsulta03;
import br.com.company.gwt.shared.dto.DTOConsulta05;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("services/propriedadeService")
public interface PropriedadeService extends RemoteService {

	List<DTOPropriedade> listAll();

	Boolean save(DTOPropriedade dtoPropriedade);

	List<DTOConsulta03> getDadosConsulta03();

	DTOPropriedade getById(Integer propriedadeId);

	List<DTOConsulta05> getDadosConsulta05();	
	
}