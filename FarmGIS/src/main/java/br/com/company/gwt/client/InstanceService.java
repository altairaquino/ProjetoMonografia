package br.com.company.gwt.client;

import com.google.gwt.core.client.GWT;

public class InstanceService {
	
	public final static UserServiceAsync USER_SERVICE = GWT.create(UserService.class);
	public final static ContatoServiceAsync CONTATO_SERVICE = GWT.create(ContatoService.class);
	public final static PropriedadeServiceAsync PROPRIEDADE_SERVICE = GWT.create(PropriedadeService.class);
	public final static ParcelaServiceAsync PARCELA_SERVICE = GWT.create(ParcelaService.class);
	public final static CulturaServiceAsync CULTURA_SERVICE = GWT.create(CulturaService.class);
	public final static CidadeServiceAsync CIDADE_SERVICE = GWT.create(CidadeService.class);

}