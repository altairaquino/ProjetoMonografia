package br.com.company.gwt.server;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import br.com.company.gwt.client.ContatoService;
import br.com.company.gwt.shared.dto.DTOContato;
import br.com.company.gwt.shared.dto.DTOUser;

@Named("contatoService")
public class ContatoServiceImpl extends InputServletImpl implements ContatoService {

	@Override
	public List<DTOContato> list(DTOUser user) {
		return new ArrayList<DTOContato>();
	}
		
}